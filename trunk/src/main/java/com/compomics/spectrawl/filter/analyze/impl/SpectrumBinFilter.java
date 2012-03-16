package com.compomics.spectrawl.filter.analyze.impl;

import com.compomics.spectrawl.filter.analyze.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 17/02/12
 * Time: 9:18
 * To change this template use File | Settings | File Templates.
 */
public class SpectrumBinFilter implements Filter<SpectrumImpl> {

    private Map<Double, Double> intensitySumFilterValues;

    public SpectrumBinFilter(Map<Double, Double> intensitySumFilterValues) {
        this.intensitySumFilterValues = intensitySumFilterValues;
    }

    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        boolean passesFilter = Boolean.TRUE;

        for (Double intensitySumFilterValue : intensitySumFilterValues.keySet()) {
            Double key = spectrum.getBins().floorKey(intensitySumFilterValue);
            if (spectrum.getBins().get(key).getIntensitySum() < intensitySumFilterValues.get(intensitySumFilterValue)) {
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
