package com.compomics.spectrawl.filter.analyze.impl;

import com.compomics.spectrawl.filter.analyze.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Peak;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 15/02/12
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
public class SpectrumMzRatioFilter implements Filter<SpectrumImpl> {

    private double mzRatioTolerance;
    private double[] mzRatioFilterValues;

    public SpectrumMzRatioFilter(double mzRatioTolerance, double[] mzRatioFilterValues) {
        this.mzRatioTolerance = mzRatioTolerance;
        this.mzRatioFilterValues = mzRatioFilterValues;
    }

    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        boolean passesFilter = Boolean.TRUE;
        for (double mzRatioFilterValue : mzRatioFilterValues) {
            if (!passesMzRatioFilterValue(spectrum, mzRatioFilterValue)) {
                passesFilter = Boolean.FALSE;
            }
        }

        if (doInvert) {
            passesFilter = !passesFilter;
        }

        return passesFilter;
    }

    private boolean passesMzRatioFilterValue(SpectrumImpl spectrum, double mzRatioFilterValue) {
        boolean passesFilterValue = Boolean.FALSE;
        for (Peak peak : spectrum.getPeakList()) {
            double mzRatio = peak.mz;
            if (Math.abs(mzRatio - mzRatioFilterValue) < mzRatioTolerance) {
                passesFilterValue = Boolean.TRUE;
                break;
            }
        }

        return passesFilterValue;
    }


}
