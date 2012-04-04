package com.compomics.spectrawl.model;


import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 9/02/12
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */
public interface Binnable<T> {
    
    /**
     * Initialize the bins;
     * construct the bins with the corresponding floor, ceiling and bin size.
     *
     */
    void initBins();

    /**
     * Gets the bins
     *
     * @return the bins
     */
    TreeMap<Double, T> getBins();

    /**
     * Gets the bin for the given key value
     *
     * @param keyValue the key value
     * @return  the bin
     */
    T getBinByKeyValue(double keyValue);
}
