package com.compomics.spectrawl.filter.process.impl;

import com.compomics.spectrawl.filter.process.NoiseThresholdFinder;
import com.compomics.spectrawl.model.FilterParams;
import com.compomics.spectrawl.util.MathUtils;

/**
 * Created by IntelliJ IDEA. User: niels Date: 6/12/11 Time: 11:21 To change
 * this template use File | Settings | File Templates.
 */
public class WinsorNoiseThresholdFinder implements NoiseThresholdFinder {
        
    ///// ///// ///// ///// ////////// ///// ///// ///// /////
    // public methods
    /**
     * Uses a winsonrisation approach (with the preset configuration) to find
     * the noise treshold for the privided signal values.
     *
     * @param signalValues list of signal values.
     * @return the calculated treshold separating noise from signal values.
     */
    @Override
    public double findNoiseThreshold(double[] signalValues) {
        // get winsorised peaks (where outlier spectrum intensities are redused to lower values)
        double[] intensities = winsorise(signalValues);

        double median = MathUtils.calculateMedian(intensities);
        double standardDeviation = MathUtils.calculateStandardDeviation(intensities);

        return median + FilterParams.WINSOR_OUTLIER_LIMIT.getValue() * standardDeviation;
    }
            
    ///// ///// ///// ///// ////////// ///// ///// ///// /////
    // private methods
    private double[] winsorise(double[] signalValues) {

        double median = MathUtils.calculateMedian(signalValues);
        double currentMAD = calcIntensityMAD(signalValues, median);
        double previousMAD = 3d * currentMAD; // initial start value
        double[] correctedIntensities = new double[signalValues.length];

        while (((previousMAD - currentMAD) / previousMAD) >= FilterParams.WINSOR_CONVERGENCE_CRITERION.getValue()) {
            correctedIntensities = reduceOutliers(signalValues, median + (FilterParams.WINSOR_CONTSTANT.getValue() * currentMAD));
            previousMAD = currentMAD;
            currentMAD = calcIntensityMAD(correctedIntensities, median);
        }

        return correctedIntensities;
    }

    private double calcIntensityMAD(double[] values, double median) {
        double[] diffs = new double[values.length];
        int cnt = 0;
        for (double p : values) {
            diffs[cnt++] = (Math.abs(p - median));
        }

        return MathUtils.calculateMedian(diffs);
    }

    private double[] reduceOutliers(double[] intensities, double maxIntensityLimit) {
        double[] correctedIntensities = new double[intensities.length];
        // sets all the values above the limit (outliers) to the limit
        // and therefore effectively eliminating outliers
        for (int i = 0; i < intensities.length; i++) {
            if (intensities[i] <= maxIntensityLimit) {
                correctedIntensities[i] = intensities[i];
            } else {
                correctedIntensities[i] = maxIntensityLimit;
            }
        }
        return correctedIntensities;
    }
}
