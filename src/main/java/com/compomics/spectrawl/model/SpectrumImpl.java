package com.compomics.spectrawl.model;

import com.compomics.util.experiment.massspectrometry.MSnSpectrum;
import com.compomics.util.experiment.massspectrometry.Peak;
import com.compomics.util.experiment.massspectrometry.Precursor;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA. User: niels Date: 12/03/12 Time: 13:47 To change
 * this template use File | Settings | File Templates.
 */
public class SpectrumImpl extends MSnSpectrum implements Binnable<SpectrumBin> {

    /**
     * The spectrum ID
     */
    private String spectrumId;
    /**
     * The spectrum bins (key: bin floor, value: spectrum bin)
     */
    private TreeMap<Double, SpectrumBin> spectrumBins;
    /**
     * The spectrum noise threshold used for filtering
     */
    private double noiseTreshold;

    public SpectrumImpl() {
        super();
    }

    public SpectrumImpl(String spectrumId) {
        super();
        this.spectrumId = spectrumId;
    }

    public SpectrumImpl(String spectrumId, String title, String fileName, Precursor precursor, HashMap<Double, Peak> peaks, double noiseThreshold) {
        super(1, precursor, title, peaks, fileName);
        this.spectrumId = spectrumId;
        this.noiseTreshold = noiseThreshold;
    }

    public SpectrumImpl(MSnSpectrum spectrum, double noiseThreshold) {
        super(spectrum.getLevel(), spectrum.getPrecursor(), spectrum.getSpectrumTitle(), spectrum.getPeakMap(), spectrum.getFileName());
        this.spectrumId = spectrum.getSpectrumKey();
        this.noiseTreshold = noiseThreshold;
    }

    public String getSpectrumId() {
        return spectrumId;
    }

    public void setSpectrumId(String spectrumId) {
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

    public double getNoiseTreshold() {
        return noiseTreshold;
    }

    public void setNoiseTreshold(double noiseTreshold) {
        this.noiseTreshold = noiseTreshold;
    }

    @Override
    public void initBins() {
        spectrumBins = new TreeMap<Double, SpectrumBin>();

        int numberOfBins = (int) ((BinParams.BINS_CEILING.getValue() - BinParams.BINS_FLOOR.getValue()) / BinParams.BIN_SIZE.getValue());
        for (int i = 0; i < numberOfBins; i++) {
            spectrumBins.put(BinParams.BINS_FLOOR.getValue() + (i * BinParams.BIN_SIZE.getValue()), new SpectrumBin());
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

//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 79 * hash + (this.spectrumId != null ? this.spectrumId.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final SpectrumImpl other = (SpectrumImpl) obj;
//        if ((this.spectrumId == null) ? (other.spectrumId != null) : !this.spectrumId.equals(other.spectrumId)) {
//            return false;
//        }
//        return true;
//    }
        
}
