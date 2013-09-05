package com.compomics.spectrawl.model.mapper;

import com.compomics.mslims.db.accessors.Spectrum_file;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Niels Hulstaert
 */
public class SpectrumFileMapper implements RowMapper<Spectrum_file> {
    
    @Override
    public Spectrum_file mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Spectrum_file(rs);
    }
}
