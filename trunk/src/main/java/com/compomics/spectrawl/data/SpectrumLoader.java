package com.compomics.spectrawl.data;

import com.compomics.spectrawl.filter.process.NoiseFilter;
import com.compomics.spectrawl.filter.process.NoiseThresholdFinder;
import com.compomics.spectrawl.model.SpectrumImpl;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 3/02/12
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public interface SpectrumLoader {
        
    /**
     * Sets the do noise filtering boolean. The default value can be set in the 
     * spectrawl.properties file.
     * 
     * @param doNoiseFiltering the do noise filtering boolean
     */
    void setDoNoiseFiltering(boolean doNoiseFiltering);
    
    /**
     * Sets the noise threshold finder for finding the spectrum noise threshold
     *
     * @param noiseThresholdFinder the noise threshold finder
     */
    void setNoiseThresholdFinder(NoiseThresholdFinder noiseThresholdFinder);

    /**
     * Sets the noise filter for filtering the spectrum
     *
     * @param noiseFilter the spectrum noise filter
     */
    void setNoiseFilter(NoiseFilter noiseFilter);

}
