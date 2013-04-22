package com.compomics.spectrawl.logic.filter.mzratio.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.logic.filter.mzratio.Filter;
import com.compomics.spectrawl.model.BinParams;
import com.compomics.spectrawl.model.PeakBin;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA. User: niels Date: 17/02/12 Time: 9:18 To change
 * this template use File | Settings | File Templates.
 */
public class FixedCombMzDeltaFilter implements Filter<SpectrumImpl> {

    private double intensityThreshold;
    /**
     * The mininum number of consecutive bins considered in order to pass the
     * filter
     */
    private int minConsecBins;
    /**
     * The maximum number of consecutive bins considered in order to pass the
     * filter
     */
    private int maxConsecBins;
    private double mzDeltaFilterValue;
    private SpectrumBinner spectrumBinner;

    public SpectrumBinner getSpectrumBinner() {
        return spectrumBinner;
    }

    public void setSpectrumBinner(SpectrumBinner spectrumBinner) {
        this.spectrumBinner = spectrumBinner;
    }

    public void init(double intensityThreshold, int minConsecBins, int maxConsecBins, double mzDeltaFilterValue) {
        this.intensityThreshold = intensityThreshold;
        this.minConsecBins = minConsecBins;
        this.maxConsecBins = maxConsecBins;
        this.mzDeltaFilterValue = mzDeltaFilterValue;
    }

    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        boolean passesFilter = true;

        //keep track of consecutive bins
        int consecBins;

        Map<Double, TreeMap<Double, PeakBin>> peakBinsMap = spectrumBinner.getPeakBinsMap(spectrum);
        outerloop:
        for (TreeMap<Double, PeakBin> peakBins : peakBinsMap.values()) {

            /**
             * look for one peak at the relevant M/Z delta values with the other
             * peaks, contained in the peakBins map. Start counting the given
             * range of consecutive M/Z delta values; break if a the right
             * amount of consecutive values is found. Restart the counting
             * otherwise.             
             */
            consecBins = 1;
            for (int i = 0; i < maxConsecBins + 1; i++) {
                //get the key based on the current M/Z delta value
                double currentMzDeltaValue = mzDeltaFilterValue * consecBins;
                Double key = peakBins.floorKey(currentMzDeltaValue);
                if (key != null && peakBins.get(key).getIntensitySum() < intensityThreshold) {
                    passesFilter = false;
                    break;
                } else {
                    consecBins++;
                }
            }
        }

        if (doInvert) {
            passesFilter = !passesFilter;
        }

        return passesFilter;
    }
}
