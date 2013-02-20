package com.compomics.spectrawl.model;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 14/02/12
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
public class Quantiles {

    private double minimum;
    private double percentile_25;
    private double percentile_50;
    private double percentile_75;
    private double maximum;

    public Quantiles(double minimum, double percentile_25, double percentile_50, double percentile_75, double maximum) {
        this.minimum = minimum;
        this.percentile_25 = percentile_25;
        this.percentile_50 = percentile_50;
        this.percentile_75 = percentile_75;
        this.maximum = maximum;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getPercentile_25() {
        return percentile_25;
    }

    public void setPercentile_25(double percentile_25) {
        this.percentile_25 = percentile_25;
    }

    public double getPercentile_50() {
        return percentile_50;
    }

    public void setPercentile_50(double percentile_50) {
        this.percentile_50 = percentile_50;
    }

    public double getPercentile_75() {
        return percentile_75;
    }

    public void setPercentile_75(double percentile_75) {
        this.percentile_75 = percentile_75;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

     public String toString(){
        return "min: " + minimum + ", 25: " + percentile_25 + ", 50: " + percentile_50 + ", 75: " + percentile_75 + ", max: " + maximum;
    }
}
