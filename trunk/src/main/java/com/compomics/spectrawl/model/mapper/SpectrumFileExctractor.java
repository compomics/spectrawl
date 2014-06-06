package com.compomics.spectrawl.model.mapper;

import com.compomics.mslims.db.accessors.Spectrum_file;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Niels Hulstaert
 */
public class SpectrumFileExctractor implements ResultSetExtractor<ConcurrentLinkedQueue<Spectrum_file>> {

    @Override
    public ConcurrentLinkedQueue<Spectrum_file> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ConcurrentLinkedQueue<Spectrum_file> spectrumFileQueue = new ConcurrentLinkedQueue<Spectrum_file>();

        while (rs.next()) {
            Spectrum_file spectrum_file = new Spectrum_file(rs);
            spectrumFileQueue.add(spectrum_file);
        }

        return spectrumFileQueue;
    }
}
