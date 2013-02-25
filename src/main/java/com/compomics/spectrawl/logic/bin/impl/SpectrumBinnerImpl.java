package com.compomics.spectrawl.logic.bin.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.model.BinParams;
import com.compomics.spectrawl.model.PeakBin;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA. User: niels Date: 28/02/12 Time: 15:04 To change
 * this template use File | Settings | File Templates.
 */
public class SpectrumBinnerImpl implements SpectrumBinner {

    @Override
    public void binSpectrum(SpectrumImpl spectrum) {
        //init bins
        spectrum.initBins();
        
        TreeMap<Double, PeakBin> peakBins = new TreeMap<Double, PeakBin>();
        for (Double outerMass : spectrum.getPeakMap().keySet()) {
            peakBins = initPeakBins(peakBins);
            for (Double innerMass : spectrum.getPeakMap().keySet()) {
                double massDelta = innerMass - outerMass;
                //check if mass delta value lies within the bins floor and ceiling
                if ((BinParams.BINS_FLOOR.getValue() <= massDelta) && (massDelta < BinParams.BINS_CEILING.getValue())) {
                    //add to peak bins
                    addToPeakBins(peakBins, massDelta, spectrum.getPeakMap().get(innerMass).intensity / spectrum.getTotalIntensity());
                }
            }
            //add peak bins to spectrum bin
            spectrum.addToBins(peakBins);
        }
    }

    /**
     * Add a mass delta to the peakBins in the correct bin
     *
     * @param peakBins the peak bins map
     * @param massDelta the mass delta
     * @param intensity the intensity
     */
    private void addToPeakBins(TreeMap<Double, PeakBin> peakBins, double massDelta, double intensity) {
        Double key = peakBins.floorKey(massDelta);
        PeakBin bin = peakBins.get(key);
        bin.addPeakCount();
        bin.addIntensity(intensity);
    }

    /**
     * Init the peak bins map
     *
     * @param peakBins the PeakBin map
     * @return the initialized peak bins map
     */
    private TreeMap<Double, PeakBin> initPeakBins(TreeMap<Double, PeakBin> peakBins) {
        peakBins.clear();

        int numberOfBins = (int) ((BinParams.BINS_CEILING.getValue() - BinParams.BINS_FLOOR.getValue()) / BinParams.BIN_SIZE.getValue());
        for (int i = 0; i < numberOfBins; i++) {
            peakBins.put(BinParams.BINS_FLOOR.getValue() + (i * BinParams.BIN_SIZE.getValue()), new PeakBin());
        }

        return peakBins;
    }
}
