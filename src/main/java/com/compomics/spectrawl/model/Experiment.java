package com.compomics.spectrawl.model;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA. User: niels Date: 14/02/12 Time: 10:36 To change
 * this template use File | Settings | File Templates.
 */
public class Experiment implements Binnable<ExperimentBin> {

    public enum ExperimentType {

        MSLIMS, MGF
    }
    private String experimentId;
    private List<SpectrumImpl> spectra;
    private TreeMap<Double, ExperimentBin> experimentBins;

    public Experiment(String experimentId) {
        this.experimentId = experimentId;
    }

    public String getExperimentId() {
        return experimentId;
    }        

    public List<SpectrumImpl> getSpectra() {
        return spectra;
    }

    public void setSpectra(List<SpectrumImpl> spectra) {
        this.spectra = spectra;
    }

    public TreeMap<Double, ExperimentBin> getExperimentBins() {
        return experimentBins;
    }

    @Override
    public void initBins() {
        experimentBins = new TreeMap<Double, ExperimentBin>();

        int numberOfBins = (int) ((BinConstants.BINS_CEILING.getValue() - BinConstants.BINS_FLOOR.getValue()) / BinConstants.BIN_SIZE.getValue());
        for (int i = 0; i < numberOfBins; i++) {
            experimentBins.put(BinConstants.BINS_FLOOR.getValue() + (i * BinConstants.BIN_SIZE.getValue()), new ExperimentBin());
        }
    }

    @Override
    public TreeMap<Double, ExperimentBin> getBins() {
        return experimentBins;
    }

    @Override
    public ExperimentBin getBinByKeyValue(double keyValue) {
        if (experimentBins == null) {
            throw new IllegalStateException("The bins have not been initialized.");
        }
        Double floorKey = experimentBins.floorKey(keyValue);
        if (floorKey == null) {
            throw new IllegalArgumentException("No bin was found for the given key value");
        }
        return experimentBins.get(floorKey);
    }
}
