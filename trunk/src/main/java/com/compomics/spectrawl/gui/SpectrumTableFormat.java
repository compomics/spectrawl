package com.compomics.spectrawl.gui;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.gui.AdvancedTableFormat;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.Comparator;

/**
 *
 * @author Niels Hulstaert
 */
public class SpectrumTableFormat implements AdvancedTableFormat<SpectrumImpl> {

    private static final String[] columnNames = {"ID", "File name", "Precursor M/Z", "Charge"};
    public static final int SPECTRUM_ID = 0;
    public static final int FILE_NAME = 1;
    public static final int PRECURSOR_MZ = 2;
    public static final int CHARGE = 3;

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case SPECTRUM_ID:
                return String.class;
            case FILE_NAME:
                return String.class;
            case PRECURSOR_MZ:
                return Double.class;
            case CHARGE:
                return Integer.class;
            default:
                throw new IllegalArgumentException("Unexpected column number " + column);
        }
    }

    @Override
    public Comparator getColumnComparator(int column) {
        return GlazedLists.comparableComparator();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getColumnValue(SpectrumImpl spectrum, int column) {
        switch (column) {
            case SPECTRUM_ID:
                return spectrum.getSpectrumId();
            case FILE_NAME:
                return spectrum.getFileName();
            case PRECURSOR_MZ:
                return spectrum.getPrecursor().getMz();
            case CHARGE:
                if (!spectrum.getPrecursor().getPossibleCharges().isEmpty()) {
                    return spectrum.getPrecursor().getPossibleCharges().get(0).value;
                } else {
                    return 0;
                }
            default:
                throw new IllegalArgumentException("Unexpected column number " + column);
        }
    }

}
