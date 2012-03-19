package com.compomics.spectrawl.model;

import com.compomics.util.experiment.massspectrometry.Spectrum;

import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 12/03/12
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */
public class SpectrumImpl extends Spectrum implements Binnable<SpectrumBin> {

    private long spectrumId;
    private TreeMap<Double, SpectrumBin> spectrumBins;

    public SpectrumImpl(long spectrumId) {
        this.spectrumId = spectrumId;
    }

    /**
     * Add the peak bins to the corresponding spectrum bins
     *
     * @param peakBins the peak bins to add
     */
    public void addToBins(TreeMap<Double, PeakBin> peakBins) {
        for (Double binFloor : spectrumBins.keySet()) {
            PeakBin peakBin = peakBins.get(binFloor);
            SpectrumBin spectrumBin = spectrumBins.get(binFloor);

            spectrumBin.addPeakCount(peakBin.getPeakCount());
            spectrumBin.addHighestIntensity(peakBin.getHighestIntensity());
            spectrumBin.addIntensitySum(peakBin.getIntensitySum());
        }
    }

    @Override
    public void initBins() {
        spectrumBins = new TreeMap<Double, SpectrumBin>();

        int numberOfBins = (int) ((Binnable.CEILING - Binnable.FLOOR) / Binnable.BIN_SIZE);
        for (int i = 0; i < numberOfBins; i++) {
            spectrumBins.put(Binnable.FLOOR + (i * Binnable.BIN_SIZE), new SpectrumBin());
        }
    }

    @Override
    public TreeMap<Double, SpectrumBin> getBins() {
        return spectrumBins;
    }

    @Override
    public SpectrumBin getBinByKeyValue(double keyValue) {
        if (spectrumBins == null) {
            throw new IllegalStateException("The bins have not been initialized.");
        }
        Double floorKey = spectrumBins.floorKey(keyValue);
        if (floorKey == null) {
            throw new IllegalArgumentException("No bin was found for the given key value");
        }
        return spectrumBins.get(floorKey);
    }
}
