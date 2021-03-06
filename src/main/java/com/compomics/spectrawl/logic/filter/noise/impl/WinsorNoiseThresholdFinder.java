package com.compomics.spectrawl.logic.filter.noise.impl;

import com.compomics.spectrawl.logic.filter.noise.NoiseThresholdFinder;
import com.compomics.spectrawl.model.FilterParams;
import com.compomics.spectrawl.util.MathUtils;
import com.google.common.primitives.Doubles;

/**
 * Created by IntelliJ IDEA. User: niels Date: 6/12/11 Time: 11:21 To change
 * this template use File | Settings | File Templates.
 */
public class WinsorNoiseThresholdFinder implements NoiseThresholdFinder {

    /**
     * There are different 3 spectrum scenarios this noise threshold finder
     * considers: 1. a spectrum with few signal peaks compared to the noise
     * peaks 2. a spectrum consisting out of signal peaks only 3. a spectrum
     * consisting out of noise peaks only
     *
     * @param signalValues the double array of signal values
     * @return the double threshold value
     */
    @Override
    public double findNoiseThreshold(double[] signalValues) {
        /**
         * the value to multiply the standard deviation with to define the
         * outlier
         */
        double noiseThreshold = 0.0;

        //calculate mean and standard deviation
        double mean = MathUtils.calculateMean(signalValues);
        double standardDeviation = Math.sqrt(MathUtils.calcVariance(signalValues, mean));

        //first use a winsonrisation approach (with the preset configuration) to find
        //the noise treshold for the privided signal values.
        double[] winsorisedValues = winsorise(signalValues);

        double winsorisedMedian = MathUtils.calculateMedian(winsorisedValues);
        double winsorisedMean = MathUtils.calculateMean(winsorisedValues);
        double winsorisedStandardDeviation = Math.sqrt(MathUtils.calcVariance(winsorisedValues, winsorisedMean));

        //check if the winsorised mean to mean ratio exceeds a given threshold
        double meanRatio = ((winsorisedMean - 0.0) < 0.001) ? 0.0 : winsorisedMean / mean;
//        double standardDeviationRatio = ((winsorisedStandardDeviation - 0.0) < 0.001) ? 0.0 : winsorisedStandardDeviation / standardDeviation;

        if (meanRatio < FilterParams.WINSOR_MEAN_RATIO_THRESHOLD.getValue()) {
            //scenario 1, the winsorisation has significantly decreased the mean
            //calculate the noise threshold for the spectrum (based on the winsorisation result)
            noiseThreshold = winsorisedMedian + FilterParams.WINSOR_OUTLIER_LIMIT.getValue() * winsorisedStandardDeviation;
        } //scenario 2 or 3
        //to make a distinction between the only signal or only noise spectrum, check the peak density
        else {
            double minimumValue = Doubles.min(signalValues);
            double maximumValue = Doubles.max(signalValues);
            //peak density: number of peaks / dalton
            double density = signalValues.length / (maximumValue - minimumValue);
            if (density < FilterParams.WINSOR_DENSITY_THRESHOLD.getValue()) {
                //scenario 2
                noiseThreshold = Math.max(mean - (1.5 * standardDeviation), 0.0);
            } else {
                //scenario 3
                noiseThreshold = mean + 1.5 * standardDeviation;
            }
        }

        return noiseThreshold;
    }

    ///// ///// ///// ///// ////////// ///// ///// ///// /////
    // private methods
    private double[] winsorise(double[] signalValues) {
        double median = MathUtils.calculateMedian(signalValues);
        double currMAD = calcIntensityMAD(signalValues, median);
        double prevMAD = 3d * currMAD; //initial start value
        double[] correctedIntensities = new double[signalValues.length];

        while (((prevMAD - currMAD) / prevMAD) >= FilterParams.WINSOR_CONVERGENCE_CRITERION.getValue()) {
            correctedIntensities = reduceOutliers(signalValues, median + (FilterParams.WINSOR_CONSTANT.getValue() * currMAD));
            prevMAD = currMAD;
            currMAD = calcIntensityMAD(correctedIntensities, median);

            //ToDo: history reporting?
            //if we want to record the history, we can here capture the current treshold (possibly together with the iteration, if not: give e.g. a list)
            //double hypotheticalNoiseThreshold = median + OUTLIER_TH * currMAD;
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
        //sets all the values above the limit (outliers) to the limit
        //and therefore effectively eliminating outliers
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
