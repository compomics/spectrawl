package com.compomics.spectrawl.filter.analyze.impl;

import com.compomics.spectrawl.filter.analyze.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 17/02/12
 * Time: 9:18
 * To change this template use File | Settings | File Templates.
 */
public class SpectrumBinFilter implements Filter<SpectrumImpl> {
    
    private double intensityThreshold;
    private List<Double> intensitySumFilterValues;

    public SpectrumBinFilter(double intensityThreshold, List<Double> intensitySumFilterValues) {
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
        boolean passesFilter = Boolean.TRUE;

        for (Double intensitySumFilterValue : intensitySumFilterValues) {
            Double key = spectrum.getBins().floorKey(intensitySumFilterValue);
            if (spectrum.getBins().get(key).getIntensitySum() < intensityThreshold) {
                passesFilter = Boolean.FALSE;
                break;
            }
        }

        if (doInvert) {
            passesFilter = !passesFilter;
        }

        return passesFilter;
    }
}
