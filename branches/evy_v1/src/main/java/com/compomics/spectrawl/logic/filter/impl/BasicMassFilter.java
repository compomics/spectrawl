package com.compomics.spectrawl.logic.filter.impl;

import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Peak;
import java.util.List;

/**
 * This filter looks for the presence of given M/Z ratio values in a spectrum.
 */
public class BasicMassFilter implements Filter<SpectrumImpl> {

    //@TODO: add a name to the filter?
    private double massTolerance;
    private List<Double> massFilterValues;

    public BasicMassFilter() {
    }

    public BasicMassFilter(double massTolerance, List<Double> massFilterValues) {
        this.massTolerance = massTolerance;
        this.massFilterValues = massFilterValues;
    }

    public double getMassTolerance() {
        return massTolerance;
    }

    public void setMassTolerance(double massTolerance) {
        this.massTolerance = massTolerance;
    }

    public List<Double> getMassFilterValues() {
        return massFilterValues;
    }

    public void setMassFilterValues(List<Double> massFilterValues) {
        this.massFilterValues = massFilterValues;
    }

    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        boolean passesFilter = true;
        for (double massFilterValue : massFilterValues) {
            if (!passesMassFilterValue(spectrum, massFilterValue)) {
                passesFilter = false;
                break;
            }
        }

        if (doInvert) {
            passesFilter = !passesFilter;
        }

        return passesFilter;
    }

    /**
     * Check if a given mass filter value is found in a spectrum
     * 
     * @param spectrum the spectrum to filter
     * @param massFilterValue the peak mass filter value
     * @return 
     */
    private boolean passesMassFilterValue(SpectrumImpl spectrum, double massFilterValue) {
        boolean passesMassFilterValue = false;
        for (Peak peak : spectrum.getPeakList()) {
            double mzRatio = peak.mz;
            //@todo consider twice the tolerance or not?
            //adjust peak M/Z value with the precursor charge
            if (Math.abs((mzRatio * spectrum.getCharge()) - massFilterValue) < massTolerance) {
                passesMassFilterValue = true;
                break;
            }
        }
        return passesMassFilterValue;
    }
}
