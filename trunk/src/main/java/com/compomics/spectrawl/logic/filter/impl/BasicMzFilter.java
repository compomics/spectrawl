package com.compomics.spectrawl.logic.filter.impl;

import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Peak;
import java.util.List;

/**
 * This filter looks for the presence of given m/z ratio values in a spectrum.
 */
public class BasicMzFilter implements Filter<SpectrumImpl> {

    //@TODO: add a name to the filter?
    private double mzTolerance;
    private List<Double> mzFilterValues;

    public BasicMzFilter() {
    }

    public BasicMzFilter(double mzTolerance, List<Double> mzFilterValues) {
        this.mzTolerance = mzTolerance;
        this.mzFilterValues = mzFilterValues;
    }

    public double getMzTolerance() {
        return mzTolerance;
    }

    public void setMzTolerance(double mzTolerance) {
        this.mzTolerance = mzTolerance;
    }

    public List<Double> getMzFilterValues() {
        return mzFilterValues;
    }

    public void setMzFilterValues(List<Double> mzFilterValues) {
        this.mzFilterValues = mzFilterValues;
    }

    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        boolean passesFilter = true;
        for (double mzFilterValue : mzFilterValues) {
            if (!passesMzFilterValue(spectrum, mzFilterValue)) {
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
     * Check if a given m/z filter value is found in a spectrum
     * 
     * @param spectrum the spectrum to filter
     * @param mzFilterValue the peak m/z filter value
     * @return 
     */
    private boolean passesMzFilterValue(SpectrumImpl spectrum, double mzFilterValue) {
        boolean passesMzFilterValue = false;
        for (Peak peak : spectrum.getPeakList()) {            
            //@todo consider twice the tolerance or not?            
            if (Math.abs(peak.mz - mzFilterValue) < mzTolerance) {
                passesMzFilterValue = true;
                break;
            }
        }
        return passesMzFilterValue;
    }
}
