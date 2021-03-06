package com.compomics.spectrawl.filter;

import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.logic.filter.impl.PrecRelMassFilter;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Charge;
import com.compomics.util.experiment.massspectrometry.Peak;
import com.compomics.util.experiment.massspectrometry.Precursor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by IntelliJ IDEA. User: niels Date: 16/02/12 Time: 16:55 To change
 * this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spectrawl-context.xml")
public class PrecRelMassFilterTest {

    private List<SpectrumImpl> spectra;

    @Before
    public void setUp() {

        //compose spectra and add them to the experiment
        HashMap<Double, Peak> peaks = new HashMap<>();
        Peak peak = new Peak(100D, 70D);
        peaks.put(100D, peak);
        peak = new Peak(220.54, 30D);
        peaks.put(220.54, peak);
        peak = new Peak(220.5, 40D);
        peaks.put(220.5, peak);
        peak = new Peak(230D, 100D);
        peaks.put(230D, peak);
        peak = new Peak(300D, 100D);
        peaks.put(300D, peak);
        peak = new Peak(420.6, 100D);
        peaks.put(420.6, peak);
        peak = new Peak(450.6, 100D);
        peaks.put(450.6, peak);

        SpectrumImpl spectrum_1 = new SpectrumImpl("1");
        ArrayList<Charge> possibleCharges = new ArrayList<>();
        possibleCharges.add(new Charge(Charge.PLUS, 1));
        Precursor precursor = new Precursor(0.0, 430.6, 0.0, possibleCharges);
        spectrum_1.setPrecursor(precursor);
        spectrum_1.setPeakList(peaks);

        peaks = new HashMap<>();
        peak = new Peak(100.0 / 2, 70D);
        peaks.put(100.0 / 2, peak);
        peak = new Peak(220.54 / 2, 30D);
        peaks.put(220.54 / 2, peak);
        peak = new Peak(220.5 / 2, 40D);
        peaks.put(220.5 / 2, peak);
        peak = new Peak(230D / 2, 100D);
        peaks.put(230D / 2, peak);
        peak = new Peak(300D / 2, 100D);
        peaks.put(300D / 2, peak);
        peak = new Peak(420.6 / 2, 100D);
        peaks.put(420.6 / 2, peak);
        peak = new Peak(450.6 / 2, 100D);
        peaks.put(450.6 / 2, peak);

        SpectrumImpl spectrum_2 = new SpectrumImpl("2");
        possibleCharges = new ArrayList<>();
        possibleCharges.add(new Charge(Charge.PLUS, 2));
        precursor = new Precursor(0.0, 430.6 / 2, 0.0, possibleCharges);
        spectrum_2.setPrecursor(precursor);
        spectrum_2.setPeakList(peaks);

        peaks = new HashMap<>();
        peak = new Peak(100D / 3, 70D);
        peaks.put(100D / 3, peak);
        peak = new Peak(220.54 / 3, 30D);
        peaks.put(220.54 / 3, peak);
        peak = new Peak(220.5 / 3, 40D);
        peaks.put(220.5 / 3, peak);
        peak = new Peak(230D / 3, 100D);
        peaks.put(230D / 3, peak);
        peak = new Peak(300D / 3, 100D);
        peaks.put(300D / 3, peak);
        peak = new Peak(420.6 / 3, 100D);
        peaks.put(420.6 / 3, peak);
        peak = new Peak(450.6 / 3, 100D);
        peaks.put(450.6 / 3, peak);

        SpectrumImpl spectrum_3 = new SpectrumImpl("3");
        possibleCharges = new ArrayList<>();
        possibleCharges.add(new Charge(Charge.PLUS, 3));
        precursor = new Precursor(0.0, 430.6 / 3, 0.0, possibleCharges);
        spectrum_3.setPrecursor(precursor);
        spectrum_3.setPeakList(peaks);

        spectra = new ArrayList<>();
        spectra.add(spectrum_1);
        spectra.add(spectrum_2);
        spectra.add(spectrum_3);
    }

    /**
     * Test for spectra with charge 1, 2 and 3. The spectra come from the same
     * precursor but with different charge. So the 3 spectra should all pass the
     * same filter.
     */
    @Test
    public void testPassesFilter() {
        List<Double> massFilterValues = new ArrayList<Double>();
        massFilterValues.add(-10D);
        massFilterValues.add(20D);
        Filter<SpectrumImpl> filter = new PrecRelMassFilter(0.5, massFilterValues);

        SpectrumImpl spectrum = spectra.get(0);
        assertTrue(filter.passesFilter(spectrum, false));
        assertFalse(filter.passesFilter(spectrum, true));

        spectrum = spectra.get(1);
        assertTrue(filter.passesFilter(spectrum, false));
        assertFalse(filter.passesFilter(spectrum, true));

        spectrum = spectra.get(2);
        assertTrue(filter.passesFilter(spectrum, false));
        assertFalse(filter.passesFilter(spectrum, true));
    }
}
