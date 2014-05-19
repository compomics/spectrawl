package com.compomics.spectrawl.filter;

import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.logic.filter.impl.BasicMzDeltaFilter;
import com.compomics.spectrawl.model.BinParams;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by IntelliJ IDEA. User: niels Date: 16/02/12 Time: 16:55 To change
 * this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:springXMLConfig.xml")
public class BasicMassDeltaFilterTest {

    private SpectrumImpl spectrum;
    @Autowired
    SpectrumBinner spectrumBinner;    

    @Before
    public void setUp() {

        //compose spectra and add them to the experiment
        HashMap<Double, Peak> peaks = new HashMap<Double, Peak>();
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

        spectrum = new SpectrumImpl("1");
        ArrayList<Charge> possibleCharges = new ArrayList<Charge>();
        possibleCharges.add(new Charge(Charge.PLUS, 1));
        Precursor precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum.setPrecursor(precursor);
        spectrum.setPeakList(peaks);        

        //bin the spectra        
        spectrumBinner.binSpectrum(spectrum, BinParams.BINS_FLOOR.getValue(), BinParams.BINS_CEILING.getValue(), BinParams.BIN_SIZE.getValue());
    }
    
    @Test
    public void testPassesFilter() {
        List<Double> filterValues = new ArrayList<Double>();
        filterValues.add(120D);
        filterValues.add(200D);
        Filter<SpectrumImpl> filter = new BasicMzDeltaFilter(0.3, filterValues);
        
        assertTrue(filter.passesFilter(spectrum, false));
        assertFalse(filter.passesFilter(spectrum, true));                
    }
}
