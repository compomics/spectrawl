package com.compomics.spectrawl.model;

import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 6/12/11
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
public class AbstractBin {

    protected int peakCount;
    protected double highestIntensity;
    protected double intensitySum;

    public AbstractBin() {
        peakCount = 0;
        highestIntensity = 0;
        intensitySum = 0;
    }

    public int getPeakCount() {
        return peakCount;
    }

    public double getHighestIntensity() {
        return highestIntensity;
    }

    public double getIntensitySum() {
        return intensitySum;
    }

    public String toString() {
        return "peak count: " + peakCount + ", intensity sum: " + intensitySum + ", highest intensity: " + highestIntensity;
    }
}
