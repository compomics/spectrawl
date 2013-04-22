package com.compomics.spectrawl.model;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 14/02/12
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */
public class ExperimentBin {

    private Quantiles peakCountQuantiles;
    private Quantiles intensitySumQuantiles;
    private Quantiles highestIntensityQuantiles;

    public Quantiles getPeakCountQuantiles() {
        return peakCountQuantiles;
    }

    public void setPeakCountQuantiles(Quantiles peakCountQuantiles) {
        this.peakCountQuantiles = peakCountQuantiles;
    }

    public Quantiles getIntensitySumQuantiles() {
        return intensitySumQuantiles;
    }

    public void setIntensitySumQuantiles(Quantiles intensitySumQuantiles) {
        this.intensitySumQuantiles = intensitySumQuantiles;
    }

    public Quantiles getHighestIntensityQuantiles() {
        return highestIntensityQuantiles;
    }

    public void setHighestIntensityQuantiles(Quantiles highestIntensityQuantiles) {
        this.highestIntensityQuantiles = highestIntensityQuantiles;
    }

    @Override
    public String toString() {
        String s = "";
        if(peakCountQuantiles != null){
            s += "peak count quantiles: " + peakCountQuantiles.toString() + System.getProperty("line.separator");
        }
        if(intensitySumQuantiles != null){
            s += "intensity sum quantiles: " + intensitySumQuantiles.toString() + System.getProperty("line.separator");
        }
        if(highestIntensityQuantiles != null){
            s += "highest intensity quantiles: " + highestIntensityQuantiles.toString();
        }
        return s;
    }
}
