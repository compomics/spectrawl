package com.compomics.spectrawl.model;

import com.compomics.util.experiment.massspectrometry.MSnSpectrum;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 12/03/12
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */
public class SpectrumImpl extends MSnSpectrum implements Binnable<SpectrumBin> {

    private String spectrumId;
    private TreeMap<Double, SpectrumBin> spectrumBins;

    public SpectrumImpl(){
        super();
    }
    
    public SpectrumImpl(String spectrumId) {
        super();
        this.spectrumId = spectrumId;
    }
    
    public SpectrumImpl(MSnSpectrum mSnSpectrum){
        super(mSnSpectrum.getLevel(), mSnSpectrum.getPrecursor(), mSnSpectrum.getSpectrumTitle(), mSnSpectrum.getPeakMap(), mSnSpectrum.getFileName(), mSnSpectrum.getScanStartTime());
        this.spectrumId = spectrumTitle;
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

        int numberOfBins = (int) ((BinConstants.BINS_CEILING.getValue() - BinConstants.BINS_FLOOR.getValue()) / BinConstants.BIN_SIZE.getValue());
        for (int i = 0; i < numberOfBins; i++) {
            spectrumBins.put(BinConstants.BINS_FLOOR.getValue() + (i * BinConstants.BIN_SIZE.getValue()), new SpectrumBin());
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
