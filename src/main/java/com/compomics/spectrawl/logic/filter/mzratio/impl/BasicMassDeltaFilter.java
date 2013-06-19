package com.compomics.spectrawl.logic.filter.mzratio.impl;

import com.compomics.spectrawl.logic.filter.mzratio.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.List;

/**
 * This filter looks for the presence of given M/Z delta values in a spectrum.
 */
public class BasicMassDeltaFilter implements Filter<SpectrumImpl> {
    
    private double intensityThreshold;
    private List<Double> intensitySumFilterValues;

    public BasicMassDeltaFilter() {
    }        
    
    public BasicMassDeltaFilter(double intensityThreshold, List<Double> intensitySumFilterValues) {
        this.intensityThreshold = intensityThreshold;
        this.intensitySumFilterValues = intensitySumFilterValues;
    }

    public List<Double> getIntensitySumFilterValues() {
        return intensitySumFilterValues;
    }

    public void setIntensitySumFilterValues(List<Double> intensitySumFilterValues) {
        this.intensitySumFilterValues = intensitySumFilterValues;
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

        for (Double intensitySumFilterValue : intensitySumFilterValues) {
            Double key = spectrum.getBins().floorKey(intensitySumFilterValue);
            if (key != null && spectrum.getBins().get(key).getIntensitySum() < intensityThreshold) {
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
