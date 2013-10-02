package com.compomics.spectrawl.logic.filter.impl;

import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Peak;
import java.util.List;

/**
 * This filter looks for the presence of given M/Z ratio values relative to the
 * precursor mass in a spectrum.
 */
public class PrecRelMzFilter implements Filter<SpectrumImpl> {

    //@TODO: add a name to the filter?
    private double mzTolerance;
    private List<Double> precRelMzFilterValues;

    public PrecRelMzFilter() {
    }

    public PrecRelMzFilter(double mzTolerance, List<Double> precRelMzFilterValues) {
        this.mzTolerance = mzTolerance;
        this.precRelMzFilterValues = precRelMzFilterValues;
    }

    public List<Double> getPrecRelMzFilterValues() {
        return precRelMzFilterValues;
    }

    public void setPrecRelMzFilterValues(List<Double> precRelMzFilterValues) {
        this.precRelMzFilterValues = precRelMzFilterValues;
    }

    public double getMzTolerance() {
        return mzTolerance;
    }

    public void setMzTolerance(double mzTolerance) {
        this.mzTolerance = mzTolerance;
    }

    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        boolean passesFilter = true;
        for (double precRelMzFilterValue : precRelMzFilterValues) {
            if (!passesPrecRelMzFilterValue(spectrum, precRelMzFilterValue)) {
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
     * Check if the m/z value relative to the precursor value is found in the
     * spectrum.
     *
     * @param spectrum
     * @param precRelMzFilterValue
     * @return
     */
    private boolean passesPrecRelMzFilterValue(SpectrumImpl spectrum, double precRelMzFilterValue) {
        boolean passesPrecRelMzFilterValue = false;
        for (Peak peak : spectrum.getPeakList()) {
            //@todo consider twice the tolerance or not?
            if (Math.abs(peak.mz - (spectrum.getPrecursor().getMz() + precRelMzFilterValue)) < mzTolerance) {
                passesPrecRelMzFilterValue = true;
                break;
            }
        }

        return passesPrecRelMzFilterValue;
    }
}
