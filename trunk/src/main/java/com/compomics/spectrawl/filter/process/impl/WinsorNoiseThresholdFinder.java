package com.compomics.spectrawl.filter.process.impl;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.filter.process.NoiseThresholdFinder;
import com.compomics.spectrawl.util.MathUtils;

/**
 * Created by IntelliJ IDEA. User: niels Date: 6/12/11 Time: 11:21 To change
 * this template use File | Settings | File Templates.
 */
public class WinsorNoiseThresholdFinder implements NoiseThresholdFinder {

    private double winsorConstant;
    private double winsorOutlierLimit; // the value to multiply the standard deviation with to define the outlier
    private double winsorConvergenceCriterion;

    ///// ///// ///// ///// ////////// ///// ///// ///// /////
    // constructor
    public WinsorNoiseThresholdFinder() {
        winsorConstant = PropertiesConfigurationHolder.getInstance().getDouble("WINSOR.CONSTANT");
        winsorOutlierLimit = PropertiesConfigurationHolder.getInstance().getDouble("WINSOR.CONVERGENCE_CRITERION");
        winsorConvergenceCriterion = PropertiesConfigurationHolder.getInstance().getDouble("WINSOR.OUTLIER_LIMIT");
    }

    public WinsorNoiseThresholdFinder(double winsorisationConstant, double outlierLimit, double convergenceCriterium) {
        winsorConstant = winsorisationConstant;
        winsorOutlierLimit = outlierLimit;
        winsorConvergenceCriterion = convergenceCriterium;
    }

    ///// ///// ///// ///// ////////// ///// ///// ///// /////
    // setters
    public void setWinsorConstant(double winsorConstant) {
        this.winsorConstant = winsorConstant;
    }

    public void setWinsorConvergenceCriterion(double winsorConvergenceCriterion) {
        this.winsorConvergenceCriterion = winsorConvergenceCriterion;
    }

    public void setWinsorOutlierLimit(double winsorOutlierLimit) {
        this.winsorOutlierLimit = winsorOutlierLimit;
    }

    ///// ///// ///// ///// ////////// ///// ///// ///// /////
    // public methods
    /**
     * Uses a winsonrisation approach (with the preset configuration) to find
     * the noise treshold for the privided signal values.
     *
     * @param signalValues list of signal values.
     * @return the calculated treshold separating noise from signal values.
     */
    public double findNoiseThreshold(double[] signalValues) {
        // get winsorised peaks (where outlier spectrum intensities are redused to lower values)
        double[] intensities = winsorise(signalValues);

        double median = MathUtils.calculateMedian(intensities);
        double standardDeviation = MathUtils.calculateStandardDeviation(intensities);

        return median + winsorOutlierLimit * standardDeviation;
    }

    ///// ///// ///// ///// ////////// ///// ///// ///// /////
    // private methods
    private double[] winsorise(double[] signalValues) {

        double median = MathUtils.calculateMedian(signalValues);
        double currentMAD = calcIntensityMAD(signalValues, median);
        double previousMAD = 3d * currentMAD; // initial start value
        double[] correctedIntensities = new double[signalValues.length];

        while (((previousMAD - currentMAD) / previousMAD) >= winsorConvergenceCriterion) {
            correctedIntensities = reduceOutliers(signalValues, median + (winsorConstant * currentMAD));
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
