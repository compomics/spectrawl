package com.compomics.spectrawl.logic.filter.noise;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 6/12/11
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public interface NoiseFilter {

    /**
     * Filters the spectrum by the given noise threshold.
     *
     * @param peaks the spectrum peaks
     * @param noiseThreshold the noise threshold
     * @return the filtered peaks
     */
    Map<Double, Double> filter(Map<Double, Double> peaks, double noiseThreshold);

    /**
     * Gets the sum of the filtered intensities.
     *
     * @return the filtered intensities sum
     */
    double getFilteredIntensitiesSum();

}
