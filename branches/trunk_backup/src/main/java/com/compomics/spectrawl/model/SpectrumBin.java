package com.compomics.spectrawl.model;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 9/02/12
 * Time: 9:55
 * To change this template use File | Settings | File Templates.
 */
public class SpectrumBin extends AbstractBin {

    /**
     * Adds the peak count of a peak mass bin
     * to the spectrum mass bin.
     *
     * @param peakCount
     */
    public void addPeakCount(int peakCount) {
        this.peakCount += peakCount;
    }

    /**
     * Adds the intensity sum of a peak mass bin
     * to the spectrum mass bin.
     *
     * @param intensitySum
     */
    public void addIntensitySum(double intensitySum) {
        this.intensitySum += intensitySum;
    }

    /**
     * Adds the highest intensity of a peak mass bin
     * to the spectrum mass bin.
     *
     * @param highestIntensity
     */
    public void addHighestIntensity(double highestIntensity) {
        this.highestIntensity += highestIntensity;
    }

}
