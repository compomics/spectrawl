package com.compomics.spectrawl.logic.filter.mzratio.impl;

import com.compomics.spectrawl.logic.filter.mzratio.Filter;
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
            if (!passesMzRatioFilterValue(spectrum, massFilterValue)) {
                passesFilter = false;
                break;
            }
        }

        if (doInvert) {
            passesFilter = !passesFilter;
        }

        return passesFilter;
    }

    private boolean passesMzRatioFilterValue(SpectrumImpl spectrum, double mzRatioFilterValue) {
        boolean passesMzRatioFilterValue = Boolean.FALSE;
        for (Peak peak : spectrum.getPeakList()) {
            double mzRatio = peak.mz;
            //@todo consider twice the tolerance or not?
            if (Math.abs(mzRatio - mzRatioFilterValue) < massTolerance) {
                passesMzRatioFilterValue = Boolean.TRUE;
                break;
            }
        }

        return passesMzRatioFilterValue;
    }


}
