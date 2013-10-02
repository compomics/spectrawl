package com.compomics.spectrawl.logic.filter.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.BinParams;
import com.compomics.spectrawl.model.PeakBin;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This filter looks for a number of consecutive fixed m/z delta values between
 * peaks, between a minimum and a maximum value.
 */
@Component("fixedCombMzDeltaFilter")
public class FixedCombMzDeltaFilter implements Filter<SpectrumImpl> {

    private double intensityThreshold;
    /**
     * The mininum number of consecutive m/z delta values considered in order
     * to pass the filter
     */
    private int minConsecMzDeltas;
    /**
     * The maximum number of consecutive m/z delta values considered in order
     * to pass the filter
     */
    private int maxConsecMzDeltas;
    private double mzDeltaFilterValue;
    @Autowired
    private SpectrumBinner spectrumBinner;

    public SpectrumBinner getSpectrumBinner() {
        return spectrumBinner;
    }

    public void setSpectrumBinner(SpectrumBinner spectrumBinner) {
        this.spectrumBinner = spectrumBinner;
    }

    public void init(double intensityThreshold, int minConsecBins, int maxConsecBins, double mzDeltaFilterValue) {
        if (minConsecBins > maxConsecBins) {
            throw new IllegalArgumentException("The minimum number of consecutive bins is larger than the maximum number of consecutive bins,");
        }
        this.intensityThreshold = intensityThreshold;
        this.minConsecMzDeltas = minConsecBins;
        this.maxConsecMzDeltas = maxConsecBins;
        this.mzDeltaFilterValue = mzDeltaFilterValue;
    }

    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        boolean passesFilter = false;

        //get appropriate values for floor and ceiling
        double floor = mzDeltaFilterValue - (BinParams.BIN_SIZE.getValue() * 2);
        double ceiling = ((maxConsecMzDeltas + 1) * mzDeltaFilterValue) + (BinParams.BIN_SIZE.getValue() * 2);
        Map<Double, TreeMap<Double, PeakBin>> peakBinsMap = spectrumBinner.getPeakBinsMap(spectrum, floor, ceiling, BinParams.BIN_SIZE.getValue());
        //iterate over the peakBins map of each peak
        for (TreeMap<Double, PeakBin> peakBins : peakBinsMap.values()) {

            /**
             * look for one peak at the relevant mass delta values with the
             * other peaks, contained in the peakBins map. Start counting the
             * given range of consecutive m/z delta values; break if the a
             * certain m/z delta value is not present (below the
             * intensitythreshold).
             */
            int consecMzDeltas = 0;
            for (int i = 1; i <= maxConsecMzDeltas + 1; i++) {
                consecMzDeltas++;
                //get the key based on the current m/z delta value
                double currentMassDeltaValue = mzDeltaFilterValue * consecMzDeltas;
                Double key = peakBins.floorKey(currentMassDeltaValue);                
                if (key == null || peakBins.get(key).getIntensitySum() < intensityThreshold) {
                    //no need to go on                    
                    consecMzDeltas--;
                    break;
                }
            }
            if (consecMzDeltas < minConsecMzDeltas) {
                //do nothing
            } else if (minConsecMzDeltas <= consecMzDeltas && consecMzDeltas <= maxConsecMzDeltas) {
                //we still need to look at other peaks because the maximum number might be exceeded there, so just break the innner loop. 
                passesFilter = true;
            } else {
                //as soon as the maximum consecutive number of consecutive m/z delta values has been reached for one peak, the spectrum fails the filter.
                passesFilter = false;
                break;
            }
        }

        if (doInvert) {
            passesFilter = !passesFilter;
        }

        return passesFilter;
    }
}
