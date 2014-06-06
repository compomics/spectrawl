package com.compomics.spectrawl.logic.bin;

import com.compomics.spectrawl.model.PeakBin;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA. User: niels Date: 10/02/12 Time: 14:01 To change
 * this template use File | Settings | File Templates.
 */
public interface SpectrumBinner {

    /**
     * Fill the spectrum bins. If the mass difference between two peaks of the
     * spectrum lies between the bins floor and ceiling, it is added to the
     * corresponding bin.
     *
     * @param spectrum the spectrum
     * @param floor
     * @param ceiling
     * @param binSize
     */
    void binSpectrum(SpectrumImpl spectrum, double floor, double ceiling, double binSize);

    /**
     * Get the PeakBin map (key: m/z ratio, value: the map of PeakBin objects).
     *
     * @param spectrum the spectrum
     * @param floor
     * @param ceiling
     * @param binSize
     * @return the PeakBin map
     */
    Map<Double, TreeMap<Double, PeakBin>> getPeakBinsMap(SpectrumImpl spectrum, double floor, double ceiling, double binSize);
}
