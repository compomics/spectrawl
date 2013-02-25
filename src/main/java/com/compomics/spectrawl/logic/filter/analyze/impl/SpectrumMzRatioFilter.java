package com.compomics.spectrawl.logic.filter.analyze.impl;

import com.compomics.spectrawl.logic.filter.analyze.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Peak;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 15/02/12
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
public class SpectrumMzRatioFilter implements Filter<SpectrumImpl> {

    //@TODO: add a name to the filter?
    
    private double mzRatioTolerance;
    private List<Double> mzRatioFilterValues;

    public SpectrumMzRatioFilter(double mzRatioTolerance, List<Double> mzRatioFilterValues) {
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
        boolean passesFilter = Boolean.TRUE;
        for (double mzRatioFilterValue : mzRatioFilterValues) {
            if (!passesMzRatioFilterValue(spectrum, mzRatioFilterValue)) {
                passesFilter = Boolean.FALSE;
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
            if (Math.abs(mzRatio - mzRatioFilterValue) < mzRatioTolerance) {
                passesMzRatioFilterValue = Boolean.TRUE;
                break;
            }
        }

        return passesMzRatioFilterValue;
    }


}
