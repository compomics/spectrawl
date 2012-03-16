package com.compomics.spectrawl.data.impl;

import com.compomics.mslims.db.accessors.Spectrum;
import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.spectrawl.data.SpectrumLoader;
import com.compomics.spectrawl.filter.process.NoiseFilter;
import com.compomics.spectrawl.filter.process.NoiseThresholdFinder;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.mslims.db.accessors.Spectrum_file;
import com.compomics.mslims.util.fileio.MascotGenericFile;
import com.compomics.spectrawl.util.PeakUtils;
import com.compomics.util.experiment.massspectrometry.Peak;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 3/02/12
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
public class MsLimsSpectrumLoader implements SpectrumLoader {

    private static final Logger LOGGER = Logger.getLogger(MsLimsSpectrumLoader.class);

    private Connection connection;
    private Map<Long, Spectrum> mSLimsSpectra;
    private boolean doProcessingFilter;
    private NoiseThresholdFinder noiseThresholdFinder;
    private NoiseFilter noiseFilter;

    public MsLimsSpectrumLoader(Connection connection) {
        this.connection = connection;
        doProcessingFilter = PropertiesConfigurationHolder.getInstance().getBoolean("DO_PROCESS_FILTER");
    }

    @Override
    public SpectrumImpl getSpectrumBySpectrumId(long spectrumId) {
        SpectrumImpl spectrum = null;

        try {
            //retrieve spectrum file
            Spectrum_file spectrum_file = Spectrum_file.findFromID(spectrumId, connection);

            //retrieve MSLims spectrum from map
            com.compomics.mslims.db.accessors.Spectrum msLimsSpectrum = (com.compomics.mslims.db.accessors.Spectrum) mSLimsSpectra.get(spectrumId);

            //generate mascotgenericfile to retrieve peaks
            MascotGenericFile mascotGenericFile = new MascotGenericFile(msLimsSpectrum.getFilename(), new String(spectrum_file.getUnzippedFile()));
            //retrieve peaks
            Map<Double, Double> mSLimsPeaks = mascotGenericFile.getPeaks();

            //create new spectrum
            spectrum = new SpectrumImpl(spectrumId);

            //filter the spectrum if necessary
            if (doProcessingFilter) {
                //check if noise threshold finder and noise filter are set
                if (noiseFilter != null && noiseThresholdFinder != null) {
                    double noiseThreshold = noiseThresholdFinder.findNoiseThreshold(PeakUtils.getIntensitiesArray(mSLimsPeaks));
                    mSLimsPeaks = noiseFilter.filter(mSLimsPeaks, noiseThreshold);
                } else {
                    throw new IllegalArgumentException("NoiseFilter and/or ThresholdFinder not set");
                }
            }

            //convert Map<Double, Double> to HashMap<Double, Peak>
            HashMap<Double, Peak> peaks = new HashMap<Double, Peak>();
            Peak peak = null;
            for (Double mzRatio : mSLimsPeaks.keySet()) {
                peak = new Peak(mzRatio, mSLimsPeaks.get(mzRatio));
                peaks.put(mzRatio, peak);
            }

            spectrum.setPeakList(peaks);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return spectrum;
    }

    @Override
    public Set<Long> getSpectraIdsByExperimentId(long experimentId) {
        return getSpectraIdsByExperimentId(experimentId, -1);
    }

    @Override
    public Set<Long> getSpectraIdsByExperimentId(long experimentId, int numberOfSpectra) {
        mSLimsSpectra = new HashMap<Long, Spectrum>();

        try {
            com.compomics.mslims.db.accessors.Spectrum[] mSLimsSpectraArray = com.compomics.mslims.db.accessors.Spectrum.getAllSpectraForProject(experimentId, connection);

            if (numberOfSpectra == -1 || numberOfSpectra > mSLimsSpectraArray.length) {
                numberOfSpectra = mSLimsSpectraArray.length;
            }

            for (int i = 0; i < numberOfSpectra; i++) {
                mSLimsSpectra.put(mSLimsSpectraArray[i].getSpectrumid(), mSLimsSpectraArray[i]);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return mSLimsSpectra.keySet();
    }

    @Override
    public void setNoiseThresholdFinder(NoiseThresholdFinder noiseThresholdFinder) {
        this.noiseThresholdFinder = noiseThresholdFinder;
    }

    @Override
    public void setNoiseFilter(NoiseFilter noiseFilter) {
        this.noiseFilter = noiseFilter;
    }
}
