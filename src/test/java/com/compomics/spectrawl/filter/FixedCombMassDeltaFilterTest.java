package com.compomics.spectrawl.filter;

import com.compomics.spectrawl.logic.bin.ExperimentBinner;
import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.logic.filter.impl.FixedCombMassDeltaFilter;
import com.compomics.spectrawl.model.BinParams;
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
public class FixedCombMassDeltaFilterTest {

    @Autowired
    private SpectrumBinner spectrumBinner;
    @Autowired
    private ExperimentBinner experimentBinner;
    @Autowired
    private FixedCombMassDeltaFilter fixedCombMassDeltaFilter;
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
        peak = new Peak(100D / 2, 100D);
        peaks.put(100D / 2, peak);
        peak = new Peak(150.5 / 2, 100D);
        peaks.put(150.5 / 2, peak);
        peak = new Peak(201D / 2, 40D);
        peaks.put(201D / 2, peak);
        peak = new Peak(251.5 / 2, 100D);
        peaks.put(251.5 / 2, peak);
        peak = new Peak(302D / 2, 100D);
        peaks.put(302D / 2, peak);
        peak = new Peak(352.5 / 2, 100D);
        peaks.put(352.5 / 2, peak);

        //add some random peaks
        peak = new Peak(160.5 / 2, 100D);
        peaks.put(160.5 / 2, peak);
        peak = new Peak(235.6 / 2, 40D);
        peaks.put(235.6 / 2, peak);
        peak = new Peak(297.1 / 2, 100D);
        peaks.put(297.1 / 2, peak);
        peak = new Peak(333.6 / 2, 100D);
        peaks.put(333.6 / 2, peak);

        SpectrumImpl spectrum_2 = new SpectrumImpl("2");
        possibleCharges = new ArrayList<Charge>();
        possibleCharges.add(new Charge(Charge.PLUS, 2));
        precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum_2.setPrecursor(precursor);
        spectrum_2.setPeakList(peaks);

        //bin the spectra        
        spectrumBinner.binSpectrum(spectrum_1, BinParams.BINS_FLOOR.getValue(), BinParams.BINS_CEILING.getValue(), BinParams.BIN_SIZE.getValue());
        spectrumBinner.binSpectrum(spectrum_2, BinParams.BINS_FLOOR.getValue(), BinParams.BINS_CEILING.getValue(), BinParams.BIN_SIZE.getValue());

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
     * Test the scenario where the number of consecutive mass deltas exceeds the
     * maximum value. In this case, the spectrum should fail to pass the filter.
     */
    @Test
    public void testPassesFilter_1() {
        //init filter        
        fixedCombMassDeltaFilter.init(0.01, 2, 4, 50.5);

        Assert.assertFalse(fixedCombMassDeltaFilter.passesFilter(experiment.getSpectra().get(0), false));
        Assert.assertFalse(fixedCombMassDeltaFilter.passesFilter(experiment.getSpectra().get(1), false));
    }

    /**
     * Test the scenario where the number of consecutive mass deltas lies within
     * the specified interval. In this case, the test should pass the filter.
     */
    @Test
    public void testPassesFilter_2() {
        //init filter        
        fixedCombMassDeltaFilter.init(0.01, 4, 6, 50.5);

        Assert.assertTrue(fixedCombMassDeltaFilter.passesFilter(experiment.getSpectra().get(0), false));
        Assert.assertTrue(fixedCombMassDeltaFilter.passesFilter(experiment.getSpectra().get(1), false));
    }

    /**
     * Test the scenario where the number of consecutive mass deltas lies within
     * the specified interval and the minimum consecutive mass deltas is one. In
     * this case, the test should pass the filter.
     */
    @Test
    public void testPassesFilter_3() {
        //init filter        
        fixedCombMassDeltaFilter.init(0.01, 1, 6, 50.5);

        Assert.assertTrue(fixedCombMassDeltaFilter.passesFilter(experiment.getSpectra().get(0), false));
        Assert.assertTrue(fixedCombMassDeltaFilter.passesFilter(experiment.getSpectra().get(1), false));
    }

    /**
     * Test the scenario where the number of consecutive mass deltas lies within
     * the specified interval, and the interval contains only one value. In this
     * case, the test should pass the filter.
     */
    @Test
    public void testPassesFilter_4() {
        //init filter        
        fixedCombMassDeltaFilter.init(0.01, 5, 5, 50.5);

        Assert.assertTrue(fixedCombMassDeltaFilter.passesFilter(experiment.getSpectra().get(0), false));
        Assert.assertTrue(fixedCombMassDeltaFilter.passesFilter(experiment.getSpectra().get(1), false));
    }

    /**
     * Test the scenario where the number of consecutive mass deltas doesn't lay
     * within the specified interval. In this case, the test shouldn't pass the
     * filter.
     */
    @Test
    public void testPassesFilter_5() {
        //init filter        
        fixedCombMassDeltaFilter.init(0.01, 7, 10, 50.5);

        Assert.assertFalse(fixedCombMassDeltaFilter.passesFilter(experiment.getSpectra().get(0), false));
        Assert.assertFalse(fixedCombMassDeltaFilter.passesFilter(experiment.getSpectra().get(1), false));
    }
}
