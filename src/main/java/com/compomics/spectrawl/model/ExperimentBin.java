package com.compomics.spectrawl.model;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

/**
 * Created by IntelliJ IDEA. User: niels Date: 14/02/12 Time: 10:41 To change
 * this template use File | Settings | File Templates.
 */
public class ExperimentBin {

    private DescriptiveStatistics peakCountStatistics = new DescriptiveStatistics();
    private DescriptiveStatistics intensitySumStatistics = new DescriptiveStatistics();
    private DescriptiveStatistics highestIntensityStatistics = new DescriptiveStatistics();
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

    public DescriptiveStatistics getPeakCountStatistics() {
        return peakCountStatistics;
    }

    public void setPeakCountStatistics(DescriptiveStatistics peakCountStatistics) {
        this.peakCountStatistics = peakCountStatistics;
    }

    public DescriptiveStatistics getIntensitySumStatistics() {
        return intensitySumStatistics;
    }

    public void setIntensitySumStatistics(DescriptiveStatistics intensitySumStatistics) {
        this.intensitySumStatistics = intensitySumStatistics;
    }

    public DescriptiveStatistics getHighestIntensityStatistics() {
        return highestIntensityStatistics;
    }

    public void setHighestIntensityStatistics(DescriptiveStatistics highestIntensityStatistics) {
        this.highestIntensityStatistics = highestIntensityStatistics;
    }

    /**
     * Add SpectrumBin statistics.
     *
     * @param spectrumBin
     */
    public void addSpectrumBin(SpectrumBin spectrumBin) {
        peakCountStatistics.addValue(spectrumBin.getPeakCount());
        intensitySumStatistics.addValue(spectrumBin.getIntensitySum());
        highestIntensityStatistics.addValue(spectrumBin.getHighestIntensity());
    }

    /**
     * After all SpectrumBins have been added, calculate the quantiles.
     */
    public void calculateQuantiles() {
        peakCountQuantiles = new Quantiles(peakCountStatistics.getMin(), peakCountStatistics.getPercentile(25),
                peakCountStatistics.getPercentile(50), peakCountStatistics.getPercentile(75), peakCountStatistics.getMax());
        intensitySumQuantiles = new Quantiles(intensitySumStatistics.getMin(), intensitySumStatistics.getPercentile(25),
                intensitySumStatistics.getPercentile(50), intensitySumStatistics.getPercentile(75), intensitySumStatistics.getMax());
        highestIntensityQuantiles = new Quantiles(highestIntensityStatistics.getMin(), highestIntensityStatistics.getPercentile(25),
                highestIntensityStatistics.getPercentile(50), highestIntensityStatistics.getPercentile(75), highestIntensityStatistics.getMax());

        //clear DescriptiveStatistics
        clearDescriptiveStatistics();
    }

    private void clearDescriptiveStatistics() {
        //clear DescriptiveStatistics
        peakCountStatistics.clear();
        intensitySumStatistics.clear();
        highestIntensityStatistics.clear();
    }

    @Override
    public String toString() {
        String s = "";
        if (peakCountQuantiles != null) {
            s += "peak count quantiles: " + peakCountQuantiles.toString() + System.getProperty("line.separator");
        }
        if (intensitySumQuantiles != null) {
            s += "intensity sum quantiles: " + intensitySumQuantiles.toString() + System.getProperty("line.separator");
        }
        if (highestIntensityQuantiles != null) {
            s += "highest intensity quantiles: " + highestIntensityQuantiles.toString();
        }
        return s;
    }
}
