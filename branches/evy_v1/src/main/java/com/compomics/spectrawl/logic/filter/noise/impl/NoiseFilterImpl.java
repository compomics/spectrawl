package com.compomics.spectrawl.logic.filter.noise.impl;

import com.compomics.spectrawl.logic.filter.noise.NoiseFilter;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 6/12/11
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
@Component("noiseFilter")
public class NoiseFilterImpl implements NoiseFilter {

    private double filteredIntensitiesSum;

    @Override
    public Map<Double, Double> filter(Map<Double, Double> peaks, double noiseThreshold) {
        Map<Double, Double> filteredPeaks = new HashMap<Double, Double>();
        filteredIntensitiesSum = 0;

        for(Double intensity : peaks.keySet()){
            if(intensity >= noiseThreshold){
                filteredPeaks.put(intensity, peaks.get(intensity));
                filteredIntensitiesSum += intensity;
            }
        }

        return filteredPeaks;
    }

    @Override
    public double getFilteredIntensitiesSum() {
        return filteredIntensitiesSum;
    }
}
