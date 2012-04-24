package com.compomics.spectrawl.bin;

import com.compomics.spectrawl.bin.impl.ExperimentBinnerImpl;
import com.compomics.spectrawl.bin.impl.SpectrumBinnerImpl;
import com.compomics.spectrawl.bin.impl.SpectrumBinnerWithSortingImpl;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.ExperimentBin;
import com.compomics.spectrawl.model.SpectrumBin;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.util.experiment.massspectrometry.Peak;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 8/12/11
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class BinnerTest {

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

        SpectrumImpl spectrum_2 = new SpectrumImpl("1");
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

        SpectrumImpl spectrum_3 = new SpectrumImpl("1");
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

        SpectrumImpl spectrum_4 = new SpectrumImpl("1");
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

        SpectrumImpl spectrum_5 = new SpectrumImpl("1");
        spectrum_5.setPeakList(peaks);

        peaks = new HashMap<Double, Peak>();
        peak = new Peak(120D, 70D);
        peaks.put(120D, peak);
        peak = new Peak(220.54, 100D);
        peaks.put(220.54, peak);

        SpectrumImpl spectrum_6 = new SpectrumImpl("1");
        spectrum_6.setPeakList(peaks);

        //bin the spectra
        SpectrumBinner spectrumBinner = new SpectrumBinnerWithSortingImpl();
        spectrumBinner.binSpectrum(spectrum_1);
        spectrumBinner.binSpectrum(spectrum_2);
        spectrumBinner.binSpectrum(spectrum_3);
        spectrumBinner.binSpectrum(spectrum_4);
        spectrumBinner.binSpectrum(spectrum_5);
        spectrumBinner.binSpectrum(spectrum_6);

        //add to experiment
        List<SpectrumImpl> spectra = new ArrayList<SpectrumImpl>();
        spectra.add(spectrum_1);
        spectra.add(spectrum_2);
        spectra.add(spectrum_3);
        spectra.add(spectrum_4);
        spectra.add(spectrum_5);
        spectra.add(spectrum_6);

        experiment = new Experiment("1");
        experiment.setSpectra(spectra);

        //bin the experiment
        ExperimentBinner experimentBinner = new ExperimentBinnerImpl();
        experimentBinner.binExperiment(experiment);
    }

    /**
     * Test the binning of a spectrum
     * bin 70; count: 1, summed: 0.2272, highest: 0.2272
     * bin 79; count: 2, summed: 0.4545, highest: 0.4545
     * bin 120; count: 2, summed: 0.386, highest: 0.3181
     * bin 130; count: 1, summed: 0.2272, highest: 0.2272
     * bin 190; count: 1, summed: 0.2272, highest: 0.2272
     * bin 200; count: 2, summed: 0.4545, highest: 0.4545
     */
    @Test
    public void testSpectrumBins() {
        //get the first spectrum
        SpectrumImpl spectrum = experiment.getSpectra().get(0);

        //bin 70
        SpectrumBin spectrumBin = spectrum.getBins().get(70D);
        assertEquals(1, spectrumBin.getPeakCount());
        assertEquals(0.2272, spectrumBin.getIntensitySum(), 0.01);
        assertEquals(0.2272, spectrumBin.getHighestIntensity(), 0.01);

        //bin 79
        spectrumBin = spectrum.getBins().get(79D);
        assertEquals(2, spectrumBin.getPeakCount());
        assertEquals(0.4545, spectrumBin.getIntensitySum(), 0.01);
        assertEquals(0.4545, spectrumBin.getHighestIntensity(), 0.01);

        //bin 120
        spectrumBin = spectrum.getBins().get(120D);
        assertEquals(2, spectrumBin.getPeakCount());
        assertEquals(0.386, spectrumBin.getIntensitySum(), 0.01);
        assertEquals(0.3181, spectrumBin.getHighestIntensity(), 0.01);

        //bin 130
        spectrumBin = spectrum.getBins().get(130D);
        assertEquals(1, spectrumBin.getPeakCount());
        assertEquals(0.2272, spectrumBin.getIntensitySum(), 0.01);
        assertEquals(0.2272, spectrumBin.getHighestIntensity(), 0.01);

        //bin 190
        spectrumBin = spectrum.getBins().get(190D);
        assertEquals(1, spectrumBin.getPeakCount());
        assertEquals(0.2272, spectrumBin.getIntensitySum(), 0.01);
        assertEquals(0.2272, spectrumBin.getHighestIntensity(), 0.01);

        //bin 200
        spectrumBin = spectrum.getBins().get(200D);
        assertEquals(3, spectrumBin.getPeakCount());
        assertEquals(0.6818, spectrumBin.getIntensitySum(), 0.01);
        assertEquals(0.6818, spectrumBin.getHighestIntensity(), 0.01);
    }

    /**
     * Test the binning of one experiment
     * bin 40: peakCountQuantile: min:0, 25: 0, 50: 2, 75: 2.25, max: 3
     * intensitySumQuantile: min:0, 25: 0, 50: 0.3333, 75: 0.34375, max: 0.375
     * highestIntensityQuantile: min:0, 25: 0, 50: 0.3333, 75: 0.34375, max: 0.375
     */
    @Test
    public void testExperimentBins() {
        //bin 40
        ExperimentBin experimentBin = experiment.getExperimentBins().get(40D);
        //peak count quantiles
        assertEquals(0.0, experimentBin.getPeakCountQuantiles().getMinimum(), 0.01);
        assertEquals(0.0, experimentBin.getPeakCountQuantiles().getPercentile_25(), 0.01);
        assertEquals(2.0, experimentBin.getPeakCountQuantiles().getPercentile_50(), 0.01);
        assertEquals(2.25, experimentBin.getPeakCountQuantiles().getPercentile_75(), 0.01);
        assertEquals(3.0, experimentBin.getPeakCountQuantiles().getMaximum(), 0.01);

        //intensity sum quantiles
        assertEquals(0.0, experimentBin.getIntensitySumQuantiles().getMinimum(), 0.01);
        assertEquals(0.0, experimentBin.getIntensitySumQuantiles().getPercentile_25(), 0.01);
        assertEquals(0.3333, experimentBin.getIntensitySumQuantiles().getPercentile_50(), 0.01);
        assertEquals(0.34357, experimentBin.getIntensitySumQuantiles().getPercentile_75(), 0.01);
        assertEquals(0.375, experimentBin.getIntensitySumQuantiles().getMaximum(), 0.01);

        //highest intensity quantiles
        assertEquals(0.0, experimentBin.getHighestIntensityQuantiles().getMinimum(), 0.01);
        assertEquals(0.0, experimentBin.getHighestIntensityQuantiles().getPercentile_25(), 0.01);
        assertEquals(0.3333, experimentBin.getHighestIntensityQuantiles().getPercentile_50(), 0.01);
        assertEquals(0.34375, experimentBin.getHighestIntensityQuantiles().getPercentile_75(), 0.01);
        assertEquals(0.375, experimentBin.getHighestIntensityQuantiles().getMaximum(), 0.01);
    }
}
