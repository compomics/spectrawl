package com.compomics.spectrawl.logic.filter.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.PeakBin;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This filter looks for a number of consecutive fixed M/Z delta values between
 * peaks, between a minimum and a maximum value.
 */
@Component("fixedCombMassDeltaFilter")
public class FixedCombMassDeltaFilter implements Filter<SpectrumImpl> {

    private double intensityThreshold;
    /**
     * The mininum number of consecutive mass delta values considered in order
     * to pass the filter
     */
    private int minConsecMassDeltas;
    /**
     * The maximum number of consecutive mass delta values considered in order
     * to pass the filter
     */
    private int maxConsecMassDeltas;
    private double massDeltaFilterValue;
    @Autowired
    private SpectrumBinner spectrumBinner;

    public SpectrumBinner getSpectrumBinner() {
        return spectrumBinner;
    }

    public void setSpectrumBinner(SpectrumBinner spectrumBinner) {
        this.spectrumBinner = spectrumBinner;
    }

    public void init(double intensityThreshold, int minConsecBins, int maxConsecBins, double massDeltaFilterValue) {
        if (minConsecBins > maxConsecBins) {
            throw new IllegalArgumentException("The minimum number of consecutive bins is larger than the maximum number of consecutive bins,");
        }
        this.intensityThreshold = intensityThreshold;
        this.minConsecMassDeltas = minConsecBins;
        this.maxConsecMassDeltas = maxConsecBins;
        this.massDeltaFilterValue = massDeltaFilterValue;
    }

    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        //@todo check if the max consecutive bins makes sense with the bin ceiling value
        //else we might get in trouble with the range
        boolean passesFilter = false;

        Map<Double, TreeMap<Double, PeakBin>> peakBinsMap = spectrumBinner.getPeakBinsMap(spectrum);
        //iterate over the peakBins map of each peak
        for (TreeMap<Double, PeakBin> peakBins : peakBinsMap.values()) {

            /**
             * look for one peak at the relevant mass delta values with the
             * other peaks, contained in the peakBins map. Start counting the
             * given range of consecutive mass delta values; break if the a
             * certain mass delta value is not present (below the
             * intensitythreshold).
             */
            int consecMassDeltas = 1;
            for (int i = 1; i <= maxConsecMassDeltas + 1; i++) {
                //get the key based on the current mass delta value
                double currentMassDeltaValue = massDeltaFilterValue * consecMassDeltas;
                Double key = peakBins.floorKey(currentMassDeltaValue);
                if (key != null && peakBins.get(key).getIntensitySum() < intensityThreshold) {
                    //no need to go on
                    break;
                } else {                    
                    consecMassDeltas++;
                    if(consecMassDeltas == 5){
                        System.out.println("spectrum " + spectrum.getSpectrumId() + ", ");
                    }
                }
            }
            if (consecMassDeltas < minConsecMassDeltas) {
                //do nothing
                //System.out.println("do nothing");
            } else if (minConsecMassDeltas <= consecMassDeltas && consecMassDeltas <= maxConsecMassDeltas) {
                //we still need to look at other peaks because the maximum number might be exceeded there, so just break the innner loop. 
                passesFilter = true;
            } else {
                //as soon as the maximum consecutive number of consecutive M/Z delta values has been reached for one peak, the spectrum fails the filter.
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
