package com.compomics.spectrawl.model;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 6/12/11
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractBin {

    protected int peakCount = 0;
    protected double highestIntensity = 0;
    protected double intensitySum = 0;

    public int getPeakCount() {
        return peakCount;
    }

    public double getHighestIntensity() {
        return highestIntensity;
    }

    public double getIntensitySum() {
        return intensitySum;
    }

    @Override
    public String toString() {
        return "peak count: " + peakCount + ", intensity sum: " + intensitySum + ", highest intensity: " + highestIntensity;
    }
}
