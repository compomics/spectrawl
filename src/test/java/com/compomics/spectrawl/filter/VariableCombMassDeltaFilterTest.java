package com.compomics.spectrawl.filter;

import com.compomics.spectrawl.logic.bin.ExperimentBinner;
import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.logic.filter.impl.VariableCombMassDeltaFilter;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Charge;
import com.compomics.util.experiment.massspectrometry.Peak;
import com.compomics.util.experiment.massspectrometry.Precursor;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by IntelliJ IDEA. User: niels Date: 16/02/12 Time: 16:55 To change
 * this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:springXMLConfig.xml")
public class VariableCombMassDeltaFilterTest {

    @Autowired
    SpectrumBinner spectrumBinner;
    @Autowired
    ExperimentBinner experimentBinner;
    @Autowired
    VariableCombMassDeltaFilter variableCombMzDeltaFilter;
    private Experiment experiment;

    @Before
    public void setUp() {

        //compose spectra and add them to the experiment
        HashMap<Double, Peak> peaks = new HashMap<Double, Peak>();
        Peak peak = new Peak(100D, 100D);
        peaks.put(100D, peak);
        peak = new Peak(150.5, 100D);
        peaks.put(150.5, peak);
        peak = new Peak(180D, 40D);
        peaks.put(180D, peak);
        peak = new Peak(211.5, 100D);
        peaks.put(211.5, peak);
        peak = new Peak(302D, 100D);
        peaks.put(302D, peak);
        peak = new Peak(322.5, 100D);
        peaks.put(322.5, peak);

        //add some random peaks
        peak = new Peak(160.5, 100D);
        peaks.put(160.5, peak);
        peak = new Peak(235.6, 40D);
        peaks.put(235.6, peak);
        peak = new Peak(297.1, 100D);
        peaks.put(297.1, peak);
        peak = new Peak(333.6, 100D);
        peaks.put(333.6, peak);

        SpectrumImpl spectrum_1 = new SpectrumImpl("1");
        ArrayList<Charge> possibleCharges = new ArrayList<Charge>();
        possibleCharges.add(new Charge(Charge.PLUS, 1));
        Precursor precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum_1.setPrecursor(precursor);
        spectrum_1.setPeakList(peaks);

        peaks = new HashMap<Double, Peak>();
        peak = new Peak(100D / 3, 100D);
        peaks.put(100D / 3, peak);
        peak = new Peak(150.5 / 3, 100D);
        peaks.put(150.5 / 3, peak);
        peak = new Peak(180D / 3, 40D);
        peaks.put(180D / 3, peak);
        peak = new Peak(211.5 / 3, 100D);
        peaks.put(211.5 / 3, peak);
        peak = new Peak(302D / 3, 100D);
        peaks.put(302D / 3, peak);
        peak = new Peak(322.5 / 3, 100D);
        peaks.put(322.5 / 3, peak);

        //add some random peaks
        peak = new Peak(160.5 / 3, 100D);
        peaks.put(160.5 / 3, peak);
        peak = new Peak(235.6 / 3, 40D);
        peaks.put(235.6 / 3, peak);
        peak = new Peak(297.1 / 3, 100D);
        peaks.put(297.1 / 3, peak);
        peak = new Peak(333.6 / 3, 100D);
        peaks.put(333.6 / 3, peak);

        SpectrumImpl spectrum_2 = new SpectrumImpl("2");
        possibleCharges = new ArrayList<Charge>();
        possibleCharges.add(new Charge(Charge.PLUS, 3));
        precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum_2.setPrecursor(precursor);
        spectrum_2.setPeakList(peaks);

        //bin the spectra        
        spectrumBinner.binSpectrum(spectrum_1);
        spectrumBinner.binSpectrum(spectrum_2);

        //add to experiment
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();
        spectra.add(spectrum_1);
        spectra.add(spectrum_2);

        experiment = new Experiment("1");
        experiment.setSpectra(spectra);

        //bin the experiment
        experimentBinner.binExperiment(experiment);
    }

    /**
     * Test the scenario where the filter mass deltas are found in the spectrum.
     * In this case, the spectrum should pass the filter.
     */
    @Test
    public void testPassesFilter_1() {
        //init filter        
        double[] mzDeltaValues = {50.5, 29.5, 31.5, 90.5, 20.5};
        variableCombMzDeltaFilter.init(0.01, mzDeltaValues);

        Assert.assertTrue(variableCombMzDeltaFilter.passesFilter(experiment.getSpectra().get(0), false));
        Assert.assertTrue(variableCombMzDeltaFilter.passesFilter(experiment.getSpectra().get(1), false));
    }

    /**
     * Test the scenario where the filter mass deltas are found in the spectrum.
     * In this case, the spectrum should pass the filter.
     */
    @Test
    public void testPassesFilter_2() {
        //init filter        
        double[] mzDeltaValues = {50.5, 29.5, 31.5};
        variableCombMzDeltaFilter.init(0.01, mzDeltaValues);

        Assert.assertTrue(variableCombMzDeltaFilter.passesFilter(experiment.getSpectra().get(0), false));
        Assert.assertTrue(variableCombMzDeltaFilter.passesFilter(experiment.getSpectra().get(1), false));
    }

    /**
     * Test the scenario where the filter mass deltas are not found in the
     * spectrum. In this case, the spectrum shouldn't pass the filter.
     */
    @Test
    public void testPassesFilter_3() {
        //init filter        
        double[] mzDeltaValues = {10.3, 79.5, 37.5, 102.5, 10.5};
        variableCombMzDeltaFilter.init(0.01, mzDeltaValues);

        Assert.assertFalse(variableCombMzDeltaFilter.passesFilter(experiment.getSpectra().get(0), false));
        Assert.assertFalse(variableCombMzDeltaFilter.passesFilter(experiment.getSpectra().get(1), false));
    }
}
