package com.compomics.spectrawl.logic.filter.impl;

import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Peak;
import java.util.List;

/**
 * This filter looks for the presence of given M/Z ratio values relative to the precursor mass in a spectrum.
 */
public class PrecRelMassFilter implements Filter<SpectrumImpl> {

    //@TODO: add a name to the filter?
    
    private double massTolerance;
    private List<Double> precRelMassFilterValues;

    public PrecRelMassFilter() {
    }    
    
    public PrecRelMassFilter(double massTolerance, List<Double> precRelMassFilterValues) {
        this.massTolerance = massTolerance;
        this.precRelMassFilterValues = precRelMassFilterValues;        
    }

    public List<Double> getPrecRelMzRatioFilterValues() {
        return precRelMassFilterValues;
    }

    public void setPrecRelMzRatioFilterValues(List<Double> precRelMzRatioFilterValues) {
        this.precRelMassFilterValues = precRelMzRatioFilterValues;
    }    

    public double getMzRatioTolerance() {
        return massTolerance;
    }

    public void setMzRatioTolerance(double mzRatioTolerance) {
        this.massTolerance = mzRatioTolerance;
    }
        
    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        boolean passesFilter = true;
        for (double precRelMzRatioFilterValue : precRelMassFilterValues) {
            if (!passesPrecRelMassFilterValue(spectrum, precRelMzRatioFilterValue)) {
                passesFilter = false;
                break;
            }
        }

        if (doInvert) {
            passesFilter = !passesFilter;
        }

        return passesFilter;
    }

    private boolean passesPrecRelMassFilterValue(SpectrumImpl spectrum, double precRelMassFilterValue) {
        boolean passesPrecRelMassFilterValue = Boolean.FALSE;
        for (Peak peak : spectrum.getPeakList()) {
            int charge = spectrum.getPrecursor().getPossibleCharges().get(0).value;
            double peakMass = peak.mz * charge;
            double precursorMass = spectrum.getPrecursor().getMz() * charge;
            //@todo consider twice the tolerance or not?
            if (Math.abs(peakMass - (precursorMass + precRelMassFilterValue)) < massTolerance) {
                passesPrecRelMassFilterValue = Boolean.TRUE;
                break;
            }
        }

        return passesPrecRelMassFilterValue;
    }


}
