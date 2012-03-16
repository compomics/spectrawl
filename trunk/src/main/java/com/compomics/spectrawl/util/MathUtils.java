package com.compomics.spectrawl.util;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Florian Reisinger
 *         Date: 10-Aug-2009
 * @since $version
 */
public class MathUtils {

    public static double calculateMedian(double[] values) {
        if (values == null) {
            throw new IllegalStateException("Can not calculate median of null!");
        }

        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        for (double d : values) {
            descriptiveStatistics.addValue(d);
        }

        return descriptiveStatistics.getPercentile(50D);
    }

    public static double calculateMean(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Can not calculate mean of null!");
        }

        SummaryStatistics summaryStatistics = new SummaryStatistics();
        for (double d : values) {
            summaryStatistics.addValue(d);
        }

        return summaryStatistics.getMean();
    }

    public static double calculateStandardDeviation(double[] values) {
         if (values == null) {
            throw new IllegalArgumentException("Can not calculate standard deviation of null!");
        }

        SummaryStatistics summaryStatistics = new SummaryStatistics();
        for (double d : values) {
            summaryStatistics.addValue(d);
        }

        return summaryStatistics.getStandardDeviation();
    }

    public static double calcVariance(double[] values, double iMean) {
        double squares = 0d;
        for (double value : values) {
            squares += Math.pow( (value - iMean), 2 );
        }
        return squares / (double) values.length;
    }


    public static double[] toArray(Collection<Double> values) {
        if (values == null) {
            throw new IllegalArgumentException("No null value allowed!");
        }
        double[] result = new double[values.size()];
        int cnt = 0;
        for (Double value : values) {
            result[cnt++] = value;
        }
        return result;
    }

    public static List<Double> toList(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("No null value allowed!");
        }
        List<Double> result = new ArrayList<Double>(values.length);
        for (double value : values) {
            result.add(value);
        }
        return result;
    }

    /**
     * This method allows to check two double values if they are equal within
     * a certain error margin. E.g. it will check if the difference between the
     * two values is smaller than the specified allowedError.
     *
     * @param a the first double to compare.
     * @param b the second double to compare.
     * @param allowedError the allowed error (or difference) between the two values.
     * @return true if the two given values are equal within the allowed error.
     */
    public static boolean equalValues(double a, double b, double allowedError) {
        // the difference between the tow values a and b has to be smaller than the allowed error.
        return Math.abs(a - b) < allowedError;
    }

}
