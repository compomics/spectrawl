package com.compomics.spectrawl.logic.filter.mzratio.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.logic.filter.mzratio.Filter;
import com.compomics.spectrawl.model.PeakBin;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.Map;
import java.util.TreeMap;

/**
 * This filter looks for a number of consecutive variable M/Z delta values
 * between peaks.
 */
public class VariableCombMzDeltaFilter implements Filter<SpectrumImpl> {

    private double intensityThreshold;
    /**
     * The consecutive M/Z delta values to filter for.
     */
    private double[] mzDeltaFilterValues;
    private SpectrumBinner spectrumBinner;

    public SpectrumBinner getSpectrumBinner() {
        return spectrumBinner;
    }

    public void setSpectrumBinner(SpectrumBinner spectrumBinner) {
        this.spectrumBinner = spectrumBinner;
    }

    public void init(double intensityThreshold, double[] mzDeltaFilterValues) {
        this.intensityThreshold = intensityThreshold;
        this.mzDeltaFilterValues = mzDeltaFilterValues;
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
             * look for one peak at the relevant M/Z delta values with the other
             * peaks, contained in the peakBins map. Start counting the given
             * range of consecutive M/Z delta values; break if the a certain M/Z
             * delta value is not present (below the intensitythreshold).
             */
            int consecMzDeltas = 0;
            double currentMzDeltaValue = 0.0;
            for (int i = 0; i < mzDeltaFilterValues.length; i++) {
                //get the key based on the current M/Z delta value
                currentMzDeltaValue += mzDeltaFilterValues[i];
                Double key = peakBins.floorKey(currentMzDeltaValue);
                if (key != null && peakBins.get(key).getIntensitySum() < intensityThreshold) {
                    //no need to go on
                    break;
                }
                else{
                    consecMzDeltas++;
                }
            }
            if(consecMzDeltas == mzDeltaFilterValues.length){
                passesFilter = true;
                break;
            }
        }

        if (doInvert) {
            passesFilter = !passesFilter;
        }

        return passesFilter;
    }
}
