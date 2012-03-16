package com.compomics.spectrawl.bin;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import com.compomics.mslims.db.accessors.Spectrum;
import com.compomics.mslims.db.accessors.Spectrum_file;
import com.compomics.mslims.util.fileio.MascotGenericFile;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 5/12/11
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
public class SpectraProcessor {

    private static final Logger LOGGER = Logger.getLogger(SpectraProcessor.class);

    private Connection connection;

    public SpectraProcessor(Connection connection) {
        this.connection = connection;
    }

    public void processSpectra(long projectId) {

        try {
            File spectrumBinsFile = new File(PropertiesConfigurationHolder.getInstance().getString("DIRECTORY"), "" + projectId + "_filtered_1000.txt");

            PrintWriter pw = new PrintWriter(spectrumBinsFile);
            pw.println("spectrum_id" + "\t" + "bin_floor" + "\t" + "peak_count" + "\t" + "highest_intensity" + "\t" + "intensity_sum");

            //get all spectra by a project id
            Spectrum[] spectrumArray = Spectrum.getAllSpectraForProject(projectId, connection);

            //iterate over spectra
            for (int i = 0; i < 1000; i++) {
                Spectrum spectrum = spectrumArray[i];
                LOGGER.debug("spectrum with id " + spectrum.getSpectrumid() + " found for project " + projectId);
                Spectrum_file spectrum_file = Spectrum_file.findFromID(spectrum.getSpectrumid(), connection);

                //generate mascotgenericfile to retrieve peaks
                MascotGenericFile mascotGenericFile = new MascotGenericFile(spectrum.getFilename(), new String(spectrum_file.getUnzippedFile()));

                //get mass bins and write them to file, 2nd parameter is the total spectrum intensity
                //if 2nd parameter is null, the spectrum will be filtered
                Double totalIntensity = spectrum.getTotal_spectrum_intensity().doubleValue();
                if(!PropertiesConfigurationHolder.getInstance().getBoolean("DO_FILTER")){
                    totalIntensity = spectrum.getTotal_spectrum_intensity().doubleValue();
                }
                //Map<Double, AbstractBin> binMap = MassBinFiller.fillMassBinMap(mascotGenericFile.getPeaks(), null);
                //LOGGER.error("totalIntensity: " + spectrum.getTotal_spectrum_intensity().doubleValue());

                //for (Double binFloor : binMap.keySet()) {
                //    pw.println("" + spectrum.getSpectrumid() + "\t" + binFloor + "\t" + binMap.get(binFloor).toString());
                //}

            }
            pw.close();

            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
