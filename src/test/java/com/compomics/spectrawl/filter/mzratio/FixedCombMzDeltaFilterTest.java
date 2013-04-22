package com.compomics.spectrawl.filter.mzratio;

import com.compomics.spectrawl.logic.filter.mzratio.Filter;
import com.compomics.spectrawl.logic.bin.ExperimentBinner;
import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.logic.filter.mzratio.impl.DefaultMzDeltaFilter;
import com.compomics.spectrawl.logic.filter.mzratio.impl.FixedCombMzDeltaFilter;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Peak;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class FixedCombMzDeltaFilterTest {

    @Autowired
    SpectrumBinner spectrumBinner;
    @Autowired
    ExperimentBinner experimentBinner;
    @Autowired
    FixedCombMzDeltaFilter fixedCombMzDeltaFilter;
    private Experiment experiment;

    @Before
    public void setUp() {

        //compose spectra and add them to the experiment
        HashMap<Double, Peak> peaks = new HashMap<Double, Peak>();
        Peak peak = new Peak(100D, 100D);
        peaks.put(100D, peak);
        peak = new Peak(150.5, 100D);
        peaks.put(150.5, peak);
        peak = new Peak(201D, 40D);
        peaks.put(201D, peak);
        peak = new Peak(251.5, 100D);
        peaks.put(251.5, peak);
        peak = new Peak(302D, 100D);
        peaks.put(302D, peak);
        peak = new Peak(352.5, 100D);
        peaks.put(352.5, peak);

        SpectrumImpl spectrum_1 = new SpectrumImpl("1");
        spectrum_1.setPeakList(peaks);

        //bin the spectra        
        spectrumBinner.binSpectrum(spectrum_1);

        //add to experiment
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();
        spectra.add(spectrum_1);

        experiment = new Experiment("1");
        experiment.setSpectra(spectra);

        //bin the experiment
        experimentBinner.binExperiment(experiment);
    }

    @Test
    public void testPassesFilter() {        
        //init filter
        fixedCombMzDeltaFilter.init(0.01, 3, 6, 50.5);
        
        SpectrumImpl spectrum = experiment.getSpectra().get(0);
        
        fixedCombMzDeltaFilter.passesFilter(spectrum, false);
//        assertTrue(filter.passesFilter(spectrum, Boolean.FALSE));
//        assertFalse(filter.passesFilter(spectrum, Boolean.TRUE));
    }
}
