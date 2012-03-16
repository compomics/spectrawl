package com.compomics.spectrawl.filter.process;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 6/12/11
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public interface NoiseThresholdFinder {

    /**
     * Finds the noise threshold for a spectrum.
     *
     * @param intensities the spectrum intensities
     * @return the noise threshold
     */
    double findNoiseThreshold(double[] intensities);

}
