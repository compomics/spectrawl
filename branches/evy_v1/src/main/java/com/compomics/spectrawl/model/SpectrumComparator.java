package com.compomics.spectrawl.model;

import java.util.Comparator;

/**
 *
 * @author Niels Hulstaert
 */
public class SpectrumComparator implements Comparator<SpectrumImpl> {

    @Override
    public int compare(SpectrumImpl o1, SpectrumImpl o2) {
        return o1.getSpectrumId().compareTo(o2.getSpectrumId());
    }
}
