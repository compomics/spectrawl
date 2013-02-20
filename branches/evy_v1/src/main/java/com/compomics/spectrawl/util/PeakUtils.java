package com.compomics.spectrawl.util;

import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 14/02/12
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class PeakUtils {

    /**
     * gets the spectrum intensities as an array
     *
     * @return the intensities array
     */
    public static double[] getIntensitiesArray(Map<Double, Double> peaks) {        
        return ArrayUtils.toPrimitive(Arrays.copyOf(peaks.values().toArray(), peaks.values().toArray().length, Double[].class));
    }

}
