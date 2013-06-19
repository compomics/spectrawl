package com.compomics.spectrawl.logic.filter.mzratio.impl;

import com.compomics.spectrawl.logic.filter.mzratio.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Peak;
import java.util.List;

/**
 * This filter looks for the presence of given M/Z ratio values relative to the precursor mass in a spectrum.
 */
public class PrecRelMassFilter implements Filter<SpectrumImpl> {

    //@TODO: add a name to the filter?
    
    private double mzRatioTolerance;
    private List<Double> precRelMzRatioFilterValues;

    public PrecRelMassFilter() {
    }    
    
    public PrecRelMassFilter(double mzRatioTolerance, List<Double> precRelMzRatioFilterValues) {
        this.mzRatioTolerance = mzRatioTolerance;
        this.precRelMzRatioFilterValues = precRelMzRatioFilterValues;        
    }

    public List<Double> getPrecRelMzRatioFilterValues() {
        return precRelMzRatioFilterValues;
    }

    public void setPrecRelMzRatioFilterValues(List<Double> precRelMzRatioFilterValues) {
        this.precRelMzRatioFilterValues = precRelMzRatioFilterValues;
    }    

    public double getMzRatioTolerance() {
        return mzRatioTolerance;
    }

    public void setMzRatioTolerance(double mzRatioTolerance) {
        this.mzRatioTolerance = mzRatioTolerance;
    }
        
    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        boolean passesFilter = true;
        for (double precRelMzRatioFilterValue : precRelMzRatioFilterValues) {
            if (!passesPrecRelMzRatioFilterValue(spectrum, precRelMzRatioFilterValue)) {
                passesFilter = false;
                break;
            }
        }

        if (doInvert) {
            passesFilter = !passesFilter;
        }

        return passesFilter;
    }

    private boolean passesPrecRelMzRatioFilterValue(SpectrumImpl spectrum, double precRelMzRatioFilterValue) {
        boolean passesPrecRelMzRatioFilterValue = Boolean.FALSE;
        for (Peak peak : spectrum.getPeakList()) {
            double mzRatio = peak.mz;
            //@todo consider twice the tolerance or not?
            if (Math.abs(mzRatio - (spectrum.getPrecursorMzRatio() - precRelMzRatioFilterValue)) < mzRatioTolerance) {
                passesPrecRelMzRatioFilterValue = Boolean.TRUE;
                break;
            }
        }

        return passesPrecRelMzRatioFilterValue;
    }


}
