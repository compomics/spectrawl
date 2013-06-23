package com.compomics.spectrawl.logic.filter.noise;

import com.compomics.util.experiment.massspectrometry.Peak;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 6/12/11
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public interface NoiseFilter {

    /**
     * Filter the spectrum by the given noise threshold.
     *
     * @param peaks the spectrum peaks
     * @param noiseThreshold the noise threshold
     * @return the filtered peaks
     */
    HashMap<Double, Peak> filter(HashMap<Double, Peak> peaks, double noiseThreshold);

    /**
     * Get the sum of the filtered intensities.
     *
     * @return the filtered intensities sum
     */
    double getFilteredIntensitiesSum();

}
