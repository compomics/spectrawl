package com.compomics.spectrawl.logic.bin.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.model.PeakBin;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA. User: niels Date: 28/02/12 Time: 15:04 To change
 * this template use File | Settings | File Templates.
 */
public class SpectrumBinnerImpl implements SpectrumBinner {

    @Override
    public void binSpectrum(SpectrumImpl spectrum, double floor, double ceiling, double binSize) {
        //init bins
        spectrum.initBins();

        Map<Double, TreeMap<Double, PeakBin>> peakBinsMap = getPeakBinsMap(spectrum, floor, ceiling, binSize);
        for (TreeMap<Double, PeakBin> peakBins : peakBinsMap.values()) {
            spectrum.addToBins(peakBins);
        }
    }

    @Override
    public Map<Double, TreeMap<Double, PeakBin>> getPeakBinsMap(SpectrumImpl spectrum, double floor, double ceiling, double binSize) {
        Map<Double, TreeMap<Double, PeakBin>> peakBinsMap = new HashMap<>();

        TreeSet<Double> sortedKeys = new TreeSet<>(spectrum.getPeakMap().keySet());
        //outer loop
        for (Double outerMz : sortedKeys) {
            TreeMap<Double, PeakBin> peakBins = new TreeMap<>();
            initPeakBins(peakBins, floor, ceiling, binSize);
            //inner loop            
            for (Double innerMz : sortedKeys) {
                double mzDelta = innerMz - outerMz;
                //check if m/z delta value lies within the bins floor and ceiling
                if ((floor <= mzDelta) && (mzDelta < ceiling)) {
                    //add to peak bins
                    //addToPeakBins(peakBins, massDelta, spectrum.getPeakMap().get(innerMass).intensity / spectrum.getTotalIntensity());
                    addToPeakBins(peakBins, mzDelta, Math.sqrt(spectrum.getPeakMap().get(innerMz).intensity * spectrum.getPeakMap().get(outerMz).intensity) / spectrum.getTotalIntensity());
                } else if (mzDelta >= ceiling) {
                    break;
                }
            }
            //add peak bins to map
            peakBinsMap.put(outerMz, peakBins);
        }

        return peakBinsMap;
    }

    /**
     * Add a m/z delta to the peakBins in the correct bin
     *
     * @param peakBins the peak bins map
     * @param mzDelta the m/z delta
     * @param intensity the intensity
     */
    private void addToPeakBins(TreeMap<Double, PeakBin> peakBins, double mzDelta, double intensity) {
        Double key = peakBins.floorKey(mzDelta);
        PeakBin bin = peakBins.get(key);
        if (bin == null) {
            bin = new PeakBin();
            peakBins.put(key, bin);
        }
        bin.addPeakCount();
        bin.addIntensity(intensity);
    }

    /**
     * Init the peak bins map
     *
     * @param peakBins the PeakBin map
     */
    private void initPeakBins(TreeMap<Double, PeakBin> peakBins, double floor, double ceiling, double binSize) {
        int numberOfBins = (int) ((ceiling - floor) / binSize);
        for (int i = 0; i < numberOfBins; i++) {
            peakBins.put(floor + (i * binSize), null);
        }
    }
}
