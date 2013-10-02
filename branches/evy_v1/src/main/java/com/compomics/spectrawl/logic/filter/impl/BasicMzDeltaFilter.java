package com.compomics.spectrawl.logic.filter.impl;

import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.List;

/**
 * This filter looks for the presence of given mass delta values in a spectrum.
 */
public class BasicMzDeltaFilter implements Filter<SpectrumImpl> {

    private double intensityThreshold;
    private List<Double> mzDeltaFilterValues;

    public BasicMzDeltaFilter() {
    }

    public BasicMzDeltaFilter(double intensityThreshold, List<Double> massDeltaFilterValues) {
        this.intensityThreshold = intensityThreshold;
        this.mzDeltaFilterValues = massDeltaFilterValues;
    }

    public List<Double> getMzDeltaFilterValues() {
        return mzDeltaFilterValues;
    }

    public void setMzDeltaFilterValues(List<Double> massDeltaFilterValues) {
        this.mzDeltaFilterValues = massDeltaFilterValues;
    }

    public double getIntensityThreshold() {
        return intensityThreshold;
    }

    public void setIntensityThreshold(double intensityThreshold) {
        this.intensityThreshold = intensityThreshold;
    }

    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        boolean passesFilter = true;

        for (Double mzDeltaFilterValue : mzDeltaFilterValues) {
            Double key = spectrum.getBins().floorKey(mzDeltaFilterValue);
            //the filter should fail if the key can't be found (is null) 
            //or the intensity in the found spectrum bin is lower than the threshold
            if (key == null || spectrum.getBins().get(key).getIntensitySum() < intensityThreshold) {
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
