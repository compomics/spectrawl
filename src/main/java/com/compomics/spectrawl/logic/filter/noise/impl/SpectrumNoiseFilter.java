package com.compomics.spectrawl.logic.filter.noise.impl;

import com.compomics.spectrawl.logic.filter.noise.NoiseFilter;
import com.compomics.util.experiment.massspectrometry.Peak;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 6/12/11
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
public class SpectrumNoiseFilter implements NoiseFilter<HashMap<Double, Peak>> {

    private double filteredIntensitiesSum;
        
    @Override
    public HashMap<Double, Peak> filter(HashMap<Double, Peak> peaks, double noiseThreshold) {
        HashMap<Double, Peak> filteredPeaks = new HashMap<>();
        filteredIntensitiesSum = 0;

        for(Peak peak : peaks.values()){
            if(peak.intensity >= noiseThreshold){
                filteredPeaks.put(peak.mz, peak);
                filteredIntensitiesSum += peak.intensity;
            }
        }

        return filteredPeaks;
    }

    @Override
    public double getFilteredIntensitiesSum() {
        return filteredIntensitiesSum;
    }

}
