package com.compomics.spectrawl.gui;

import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.gui.spectrum.SpectrumPanel;

/**
 *
 * @author Niels Hulstaert
 */
public class SpectrumPanelFactory {

    /**
     * Constructs a SpectrumPanel for the given spectrum
     *
     * @param spectrum the spectrum
     * @return the constructed SpectrumPanel
     */
    public static SpectrumPanel getSpectrumPanel(SpectrumImpl spectrum) {
        //initialize new SpectrumPanel
        int charge;
        if(!spectrum.getPrecursor().getPossibleCharges().isEmpty()){
            charge = spectrum.getPrecursor().getPossibleCharges().get(0).value;
        }
        else{
            charge = 0;
        }
        
        SpectrumPanel spectrumPanel = new SpectrumPanel(spectrum.getMzValuesAsArray(),
                spectrum.getIntensityValuesAsArray(),
                spectrum.getPrecursor().getMz(),
                Integer.toString(charge), "", 40, false, false, false);

        // remove the border
        spectrumPanel.setBorder(null);

        //spectrumPanel.showAnnotatedPeaksOnly(true);

        return spectrumPanel;
    }
}
