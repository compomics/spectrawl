package com.compomics.spectrawl.bin;

import com.compomics.spectrawl.logic.bin.ExperimentBinner;
import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.ExperimentBin;
import com.compomics.spectrawl.model.SpectrumBin;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Charge;
import com.compomics.util.experiment.massspectrometry.Peak;
import com.compomics.util.experiment.massspectrometry.Precursor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by IntelliJ IDEA. User: niels Date: 8/12/11 Time: 11:19 To change
 * this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:springXMLConfig.xml")
public class BinnerTest {

    @Autowired
    private ExperimentBinner experimentBinner;
    @Autowired
    private SpectrumBinner spectrumBinner;
    private Experiment experiment;

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

        SpectrumImpl spectrum_1 = new SpectrumImpl("1");
        ArrayList<Charge> possibleCharges = new ArrayList<Charge>();
        possibleCharges.add(new Charge(Charge.PLUS, 1));
        Precursor precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum_1.setPrecursor(precursor);
        spectrum_1.setPeakList(peaks);

        peaks = new HashMap<Double, Peak>();
        peak = new Peak(100D, 70D);
        peaks.put(100D, peak);
        peak = new Peak(220.54, 100D);
        peaks.put(220.54, peak);
        peak = new Peak(220.5D, 40D);
        peaks.put(220.5D, peak);
        peak = new Peak(230D, 50D);
        peaks.put(230D, peak);
        peak = new Peak(300D, 120D);
        peaks.put(300D, peak);
        peak = new Peak(120D, 70D);
        peaks.put(120D, peak);
        peak = new Peak(240.54, 100D);
        peaks.put(240.54, peak);
        peak = new Peak(140D, 70D);
        peaks.put(140D, peak);
        peak = new Peak(260.54, 100D);
        peaks.put(260.54, peak);

        SpectrumImpl spectrum_2 = new SpectrumImpl("2");
        possibleCharges = new ArrayList<Charge>();
        possibleCharges.add(new Charge(Charge.PLUS, 1));
        precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum_2.setPrecursor(precursor);
        spectrum_2.setPeakList(peaks);

        peaks = new HashMap<Double, Peak>();
        peak = new Peak(100D, 70D);
        peaks.put(100D, peak);
        peak = new Peak(220.54, 100D);
        peaks.put(220.54, peak);
        peak = new Peak(120D, 70D);
        peaks.put(120D, peak);
        peak = new Peak(240.54, 100D);
        peaks.put(240.54, peak);
        peak = new Peak(140D, 70D);
        peaks.put(140D, peak);
        peak = new Peak(260.54, 100D);
        peaks.put(260.54, peak);

        SpectrumImpl spectrum_3 = new SpectrumImpl("3");
        possibleCharges = new ArrayList<Charge>();
        possibleCharges.add(new Charge(Charge.PLUS, 2));
        precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum_3.setPrecursor(precursor);
        spectrum_3.setPeakList(peaks);

        peaks = new HashMap<Double, Peak>();
        peak = new Peak(100D, 70D);
        peaks.put(100D, peak);
        peak = new Peak(220.54, 100D);
        peaks.put(220.54, peak);
        peak = new Peak(120D, 70D);
        peaks.put(120D, peak);
        peak = new Peak(240.54, 100D);
        peaks.put(240.54, peak);
        peak = new Peak(140D, 70D);
        peaks.put(140D, peak);
        peak = new Peak(260.54, 100D);
        peaks.put(260.54, peak);

        SpectrumImpl spectrum_4 = new SpectrumImpl("4");
        possibleCharges = new ArrayList<Charge>();
        possibleCharges.add(new Charge(Charge.PLUS, 2));
        precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum_4.setPrecursor(precursor);
        spectrum_4.setPeakList(peaks);

        peaks = new HashMap<Double, Peak>();
        peak = new Peak(100D, 70D);
        peaks.put(100D, peak);
        peak = new Peak(220.54, 100D);
        peaks.put(220.54, peak);
        peak = new Peak(120D, 70D);
        peaks.put(120D, peak);
        peak = new Peak(240.54, 100D);
        peaks.put(240.54, peak);
        peak = new Peak(140D, 70D);
        peaks.put(140D, peak);
        peak = new Peak(260.54, 100D);
        peaks.put(260.54, peak);

        SpectrumImpl spectrum_5 = new SpectrumImpl("5");
        possibleCharges = new ArrayList<Charge>();
        possibleCharges.add(new Charge(Charge.PLUS, 3));
        precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum_5.setPrecursor(precursor);
        spectrum_5.setPeakList(peaks);

        peaks = new HashMap<Double, Peak>();
        peak = new Peak(120D, 70D);
        peaks.put(120D, peak);
        peak = new Peak(220.54, 100D);
        peaks.put(220.54, peak);

        SpectrumImpl spectrum_6 = new SpectrumImpl("6");
        possibleCharges = new ArrayList<Charge>();
        possibleCharges.add(new Charge(Charge.PLUS, 3));
        precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum_6.setPrecursor(precursor);
        spectrum_6.setPeakList(peaks);

        peaks = new HashMap<Double, Peak>();
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

        SpectrumImpl spectrum_7 = new SpectrumImpl("7");
        possibleCharges = new ArrayList<Charge>();
        possibleCharges.add(new Charge(Charge.PLUS, 3));
        precursor = new Precursor(0.0, 0.0, 0.0, possibleCharges);
        spectrum_7.setPrecursor(precursor);
        spectrum_7.setPeakList(peaks);

        //bin the spectra
        spectrumBinner.binSpectrum(spectrum_1);
        spectrumBinner.binSpectrum(spectrum_2);
        spectrumBinner.binSpectrum(spectrum_3);
        spectrumBinner.binSpectrum(spectrum_4);
        spectrumBinner.binSpectrum(spectrum_5);
        spectrumBinner.binSpectrum(spectrum_6);
        spectrumBinner.binSpectrum(spectrum_7);

        //add to experiment
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();
        spectra.add(spectrum_1);
        spectra.add(spectrum_2);
        spectra.add(spectrum_3);
        spectra.add(spectrum_4);
        spectra.add(spectrum_5);
        spectra.add(spectrum_6);
        spectra.add(spectrum_7);

        experiment = new Experiment("1");
        experiment.setSpectra(spectra);

        //bin the experiment
        experimentBinner.binExperiment(experiment);
    }

    /**
     * Test if 2 spectra of the same precursor but with a different mass have
     * identical spectrum bins.
     */
    @Test
    public void testDifferentChargedPrecursor() {
        //get the 1st and the 7th spectrum
        SpectrumImpl spectrumWithCharge1 = experiment.getSpectra().get(0);
        SpectrumImpl spectrumWithCharge3 = experiment.getSpectra().get(6);

        for (Double key : spectrumWithCharge1.getBins().keySet()) {
            Assert.assertEquals(spectrumWithCharge1.getBins().get(key).getHighestIntensity(), spectrumWithCharge3.getBins().get(key).getHighestIntensity(), 0.0001);
            Assert.assertEquals(spectrumWithCharge1.getBins().get(key).getIntensitySum(), spectrumWithCharge3.getBins().get(key).getIntensitySum(), 0.0001);
            Assert.assertEquals(spectrumWithCharge1.getBins().get(key).getPeakCount(), spectrumWithCharge3.getBins().get(key).getPeakCount(), 0.0001);
        }

    }

    /**
     * Test the binning of a spectrum bin 70; count: 1, summed: 0.2272, highest:
     * 0.2272 bin 79; count: 2, summed: 0.2682, highest: 0.2682 bin 120; count:
     * 2, summed: 0.4517, highest: 0.3475 bin 130; count: 1, summed: 0.1902,
     * highest: 0.1902 bin 190; count: 1, summed: 0.2272, highest: 0.2272 bin
     * 200; count: 2, summed: 0.4584, highest: 0.4584
     */
    @Test
    public void testSpectrumBins() {
        //get the first spectrum
        SpectrumImpl spectrum = experiment.getSpectra().get(0);

        //bin 70
        SpectrumBin spectrumBin = spectrum.getBins().get(70D);
        Assert.assertEquals(1, spectrumBin.getPeakCount());
        Assert.assertEquals(0.2272, spectrumBin.getIntensitySum(), 0.01);
        Assert.assertEquals(0.2272, spectrumBin.getHighestIntensity(), 0.01);

        //bin 79
        spectrumBin = spectrum.getBins().get(79D);
        Assert.assertEquals(2, spectrumBin.getPeakCount());
        Assert.assertEquals(0.2682, spectrumBin.getIntensitySum(), 0.01);
        Assert.assertEquals(0.2682, spectrumBin.getHighestIntensity(), 0.01);

        //bin 120
        spectrumBin = spectrum.getBins().get(120D);
        Assert.assertEquals(2, spectrumBin.getPeakCount());
        Assert.assertEquals(0.4517, spectrumBin.getIntensitySum(), 0.01);
        Assert.assertEquals(0.3475, spectrumBin.getHighestIntensity(), 0.01);

        //bin 130
        spectrumBin = spectrum.getBins().get(130D);
        Assert.assertEquals(1, spectrumBin.getPeakCount());
        Assert.assertEquals(0.1902, spectrumBin.getIntensitySum(), 0.01);
        Assert.assertEquals(0.1902, spectrumBin.getHighestIntensity(), 0.01);

        //bin 190
        spectrumBin = spectrum.getBins().get(190D);
        Assert.assertEquals(1, spectrumBin.getPeakCount());
        Assert.assertEquals(0.2272, spectrumBin.getIntensitySum(), 0.01);
        Assert.assertEquals(0.2272, spectrumBin.getHighestIntensity(), 0.01);

        //bin 200
        spectrumBin = spectrum.getBins().get(200D);
        Assert.assertEquals(3, spectrumBin.getPeakCount());
        Assert.assertEquals(0.4584, spectrumBin.getIntensitySum(), 0.01);
        Assert.assertEquals(0.4584, spectrumBin.getHighestIntensity(), 0.01);
    }

    /**
     * Test the binning of one experiment bin 40: peakCountQuantile: min:0, 25:
     * 0, 50: 0, 75: 4, max: 4 intensitySumQuantile: min:0, 25: 0, 50: 0, 75:
     * 0.667, max: 0.667 highestIntensityQuantile: min:0, 25: 0, 50: 0, 75:
     * 0.667, max: 0.667
     */
    @Test
    public void testExperimentBins() {
        //bin 40
        ExperimentBin experimentBin = experiment.getExperimentBins().get(40D);
        //peak count quantiles
        Assert.assertEquals(0.0, experimentBin.getPeakCountQuantiles().getMinimum(), 0.01);
        Assert.assertEquals(0.0, experimentBin.getPeakCountQuantiles().getPercentile_25(), 0.01);
        Assert.assertEquals(0.0, experimentBin.getPeakCountQuantiles().getPercentile_50(), 0.01);
        Assert.assertEquals(4.0, experimentBin.getPeakCountQuantiles().getPercentile_75(), 0.01);
        Assert.assertEquals(4.0, experimentBin.getPeakCountQuantiles().getMaximum(), 0.01);

        //intensity sum quantiles
        Assert.assertEquals(0.0, experimentBin.getIntensitySumQuantiles().getMinimum(), 0.01);
        Assert.assertEquals(0.0, experimentBin.getIntensitySumQuantiles().getPercentile_25(), 0.01);
        Assert.assertEquals(0.0, experimentBin.getIntensitySumQuantiles().getPercentile_50(), 0.01);
        Assert.assertEquals(0.667, experimentBin.getIntensitySumQuantiles().getPercentile_75(), 0.01);
        Assert.assertEquals(0.667, experimentBin.getIntensitySumQuantiles().getMaximum(), 0.01);

        //highest intensity quantiles
        Assert.assertEquals(0.0, experimentBin.getHighestIntensityQuantiles().getMinimum(), 0.01);
        Assert.assertEquals(0.0, experimentBin.getHighestIntensityQuantiles().getPercentile_25(), 0.01);
        Assert.assertEquals(0.0, experimentBin.getHighestIntensityQuantiles().getPercentile_50(), 0.01);
        Assert.assertEquals(0.667, experimentBin.getHighestIntensityQuantiles().getPercentile_75(), 0.01);
        Assert.assertEquals(0.667, experimentBin.getHighestIntensityQuantiles().getMaximum(), 0.01);
    }
}
