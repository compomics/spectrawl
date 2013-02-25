package com.compomics.spectrawl.logic.filter.process;

import com.compomics.spectrawl.model.FilterParams;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 6/12/11
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public interface NoiseThresholdFinder {

    /**
     * Finds the noise threshold for a spectrum.
     *
     * @param intensities the spectrum intensities
     * @return the noise threshold
     */
    double findNoiseThreshold(double[] intensities);
        
}
