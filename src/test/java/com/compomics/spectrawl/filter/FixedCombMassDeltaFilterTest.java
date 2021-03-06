package com.compomics.spectrawl.filter;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.logic.filter.impl.FixedCombMassDeltaFilter;
import com.compomics.spectrawl.model.BinParams;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Charge;
import com.compomics.util.experiment.massspectrometry.Peak;
import com.compomics.util.experiment.massspectrometry.Precursor;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
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
@ContextConfiguration("classpath:spectrawl-context.xml")
public class FixedCombMassDeltaFilterTest {

    private SpectrumImpl spectrum;
    @Autowired
    private SpectrumBinner spectrumBinner;
    @Autowired
    private FixedCombMassDeltaFilter fixedCombMassDeltaFilter;

    @Before
    public void setUp() {

        //compose spectra and add them to the experiment
        HashMap<Double, Peak> peaks = new HashMap<>();
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

        spectrum = new SpectrumImpl("1");
        ArrayList<Charge> possibleCharges = new ArrayList<>();
        possibleCharges.add(new Charge(Charge.PLUS, 1));
        Precursor precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum.setPrecursor(precursor);
        spectrum.setPeakList(peaks);

        //bin the spectra        
        spectrumBinner.binSpectrum(spectrum, BinParams.BINS_FLOOR.getValue(), BinParams.BINS_CEILING.getValue(), BinParams.BIN_SIZE.getValue());
    }

    /**
     * Test the scenario where the number of consecutive mass deltas exceeds the
     * maximum value. In this case, the spectrum should fail to pass the filter.
     */
    @Test
    public void testPassesFilter_1() {
        //init filter        
        fixedCombMassDeltaFilter.init(0.01, 2, 4, 50.5);

        Assert.assertFalse(fixedCombMassDeltaFilter.passesFilter(spectrum, false));
    }

    /**
     * Test the scenario where the number of consecutive mass deltas lies within
     * the specified interval. In this case, the test should pass the filter.
     */
    @Test
    public void testPassesFilter_2() {
        //init filter        
        fixedCombMassDeltaFilter.init(0.01, 4, 6, 50.5);

        Assert.assertTrue(fixedCombMassDeltaFilter.passesFilter(spectrum, false));
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

        Assert.assertTrue(fixedCombMassDeltaFilter.passesFilter(spectrum, false));
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

        Assert.assertTrue(fixedCombMassDeltaFilter.passesFilter(spectrum, false));
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

        Assert.assertFalse(fixedCombMassDeltaFilter.passesFilter(spectrum, false));
    }

    /**
     * Test the scenario where the number of consecutive mass deltas lies within
     * the specified interval but the intensities are below the intensity
     * threshold. In this case, the test shoulddn't pass the filter.
     */
    @Test
    public void testPassesFilter_6() {
        //init filter        
        fixedCombMassDeltaFilter.init(0.5, 4, 6, 50.5);

        Assert.assertFalse(fixedCombMassDeltaFilter.passesFilter(spectrum, false));
    }
}
