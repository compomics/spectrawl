package com.compomics.spectrawl.logic.bin.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.model.BinParams;
import com.compomics.spectrawl.model.PeakBin;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA. User: niels Date: 28/02/12 Time: 15:04 To change
 * this template use File | Settings | File Templates.
 */
@Component("spectrumBinner")
public class SpectrumBinnerImpl implements SpectrumBinner {

    @Override
    public void binSpectrum(SpectrumImpl spectrum) {
        //init bins
        spectrum.initBins();
        
        Map<Double, TreeMap<Double, PeakBin>> peakBinsMap = getPeakBinsMap(spectrum);
        for(TreeMap<Double, PeakBin> peakBins : peakBinsMap.values()){
            spectrum.addToBins(peakBins);
        }
    }

    @Override
    public Map<Double, TreeMap<Double, PeakBin>> getPeakBinsMap(SpectrumImpl spectrum) {
        Map<Double, TreeMap<Double, PeakBin>> peakBinsMap = new HashMap<Double, TreeMap<Double, PeakBin>>();

        TreeSet<Double> sortedKeys = new TreeSet<Double>(spectrum.getPeakMap().keySet());
        //outer loop
        for (Double outerMass : sortedKeys) {
            TreeMap<Double, PeakBin> peakBins = new TreeMap<Double, PeakBin>();
            initPeakBins(peakBins);
            //inner loop            
            for (Double innerMass : sortedKeys) {
                double massDelta = innerMass - outerMass;
                //check if mass delta value lies within the bins floor and ceiling
                if ((BinParams.BINS_FLOOR.getValue() <= massDelta) && (massDelta < BinParams.BINS_CEILING.getValue())) {
                    //add to peak bins
                    //addToPeakBins(peakBins, massDelta, spectrum.getPeakMap().get(innerMass).intensity / spectrum.getTotalIntensity());
                    addToPeakBins(peakBins, massDelta, Math.sqrt(spectrum.getPeakMap().get(innerMass).intensity * spectrum.getPeakMap().get(outerMass).intensity) / spectrum.getTotalIntensity());
                } else if (massDelta >= BinParams.BINS_CEILING.getValue()) {
                    break;
                }
            }
            //add peak bins to map
            peakBinsMap.put(outerMass, peakBins);
        }

        return peakBinsMap;
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
     */
    private void initPeakBins(TreeMap<Double, PeakBin> peakBins) {
        int numberOfBins = (int) ((BinParams.BINS_CEILING.getValue() - BinParams.BINS_FLOOR.getValue()) / BinParams.BIN_SIZE.getValue());
        for (int i = 0; i < numberOfBins; i++) {
            peakBins.put(BinParams.BINS_FLOOR.getValue() + (i * BinParams.BIN_SIZE.getValue()), new PeakBin());
        }
    }
}
