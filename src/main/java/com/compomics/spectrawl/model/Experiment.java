package com.compomics.spectrawl.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();
    private TreeMap<Double, ExperimentBin> experimentBins;
    private int numberOfSpectra;
    private int numberOfFilteredSpectra;

    public Experiment(String experimentId) {
        this.experimentId = experimentId;
    }

    public String getExperimentId() {
        return experimentId;
    }

    public int getNumberOfFilteredSpectra() {
        return numberOfFilteredSpectra;
    }

    public void setNumberOfFilteredSpectra(int numberOfFilteredSpectra) {
        this.numberOfFilteredSpectra = numberOfFilteredSpectra;
    }

    public int getNumberOfSpectra() {
        return numberOfSpectra;
    }

    public void setNumberOfSpectra(int numberOfSpectra) {
        this.numberOfSpectra = numberOfSpectra;
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

    public void addSpectrum(SpectrumImpl spectrum) {
        spectra.add(spectrum);

        //iterate over bins
        for (Map.Entry<Double, ExperimentBin> entry : experimentBins.entrySet()) {
            ExperimentBin experimentBin = entry.getValue();
            SpectrumBin spectrumBin = spectrum.getBins().get(entry.getKey());
            experimentBin.addSpectrumBin(spectrumBin);
        }

        //clear spectrumBins
        spectrum.getBins().clear();
    }

    public void calculateQuantiles() {
        //iterate over bins
        for (Map.Entry<Double, ExperimentBin> entry : experimentBins.entrySet()) {
            ExperimentBin experimentBin = entry.getValue();

            experimentBin.calculateQuantiles();
        }
    }

    @Override
    public void initBins() {
        experimentBins = new TreeMap<Double, ExperimentBin>();

        int numberOfBins = (int) ((BinParams.BINS_CEILING.getValue() - BinParams.BINS_FLOOR.getValue()) / BinParams.BIN_SIZE.getValue());
        for (int i = 0; i < numberOfBins; i++) {
            experimentBins.put(BinParams.BINS_FLOOR.getValue() + (i * BinParams.BIN_SIZE.getValue()), new ExperimentBin());
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
