package com.compomics.spectrawl.util;

import com.compomics.util.experiment.massspectrometry.Peak;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
     * get the spectrum intensities as an array
     *
     * @param peaks the peak map (key: M/Z ratio, value: intensity)
     * @return the intensities array
     */
    public static double[] getIntensitiesArray(Map<Double, Double> peaks) {        
        return ArrayUtils.toPrimitive(Arrays.copyOf(peaks.values().toArray(), peaks.values().toArray().length, Double[].class));
    }

    
    /**
     * get the spectrum intensities as an array
     *
     * @param peaks the utilities peak map
     * @return the intensities array
     */
    public static double[] getIntensitiesArrayFromPeakList(HashMap<Double, Peak> peaks) {        
        double[] intensities = new double[peaks.size()];
        int i = 0;
        for(Peak peak : peaks.values()){
            intensities[i] = peak.intensity;
            i++;
        }
        
        return intensities;
    }
}
