package com.compomics.spectrawl.model;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 9/02/12
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
public class PeakBin extends AbstractBin {

    /**
     * Adds an intensity to the bin
     * and check if it's greater then the highest one present.
     *
     * @param intensity the peak intensity
     */
    public void addIntensity(double intensity){
        intensitySum += intensity;
        if(intensity > highestIntensity){
            highestIntensity = intensity;
        }
    }

    /**
     * Adds a peak count to the bin.
     * If the bin contains one or more peaks,
     * the count is 1, else 0.
     *
     */
    public void addPeakCount(){
        if(peakCount == 0){
            peakCount = 1;
        }
    }
}
