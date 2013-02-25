package com.compomics.spectrawl.logic.bin;

import com.compomics.spectrawl.model.SpectrumImpl;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 10/02/12
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public interface SpectrumBinner {

    /**
     * Fills the spectrum bins. If the mass difference between two peaks of the spectrum
     * lies between the bins floor and ceiling, it is added to the corresponding bin.
     *
     * @param spectrum the spectrum
     */
    void binSpectrum(SpectrumImpl spectrum);

}
