package com.compomics.spectrawl.logic.filter.mzratio.impl;

import com.compomics.spectrawl.logic.filter.mzratio.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Peak;
import java.util.List;

/**
 * This filter looks for the presence of given M/Z ratio values relative to the precursor mass in a spectrum.
 */
public class PrecursorMzRatioFilter implements Filter<SpectrumImpl> {

    //@TODO: add a name to the filter?
    
    private double mzRatioTolerance;
    private List<Double> mzRatioFilterValues;

    public PrecursorMzRatioFilter() {
    }    
    
    public PrecursorMzRatioFilter(double mzRatioTolerance, List<Double> mzRatioFilterValues) {
        this.mzRatioTolerance = mzRatioTolerance;
        this.mzRatioFilterValues = mzRatioFilterValues;        
    }

    public List<Double> getMzRatioFilterValues() {
        return mzRatioFilterValues;
    }

    public void setMzRatioFilterValues(List<Double> mzRatioFilterValues) {
        this.mzRatioFilterValues = mzRatioFilterValues;
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
        for (double mzRatioFilterValue : mzRatioFilterValues) {
            if (!passesMzRatioFilterValue(spectrum, mzRatioFilterValue)) {
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
            if (Math.abs(mzRatio - mzRatioFilterValue) < mzRatioTolerance) {
                passesMzRatioFilterValue = Boolean.TRUE;
                break;
            }
        }

        return passesMzRatioFilterValue;
    }


}
