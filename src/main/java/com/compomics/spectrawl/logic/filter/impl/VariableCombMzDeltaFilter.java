package com.compomics.spectrawl.logic.filter.impl;

import com.compomics.spectrawl.logic.bin.SpectrumBinner;
import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.BinParams;
import com.compomics.spectrawl.model.PeakBin;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This filter looks for a number of consecutive variable M/Z delta values
 * between peaks.
 */
@Component("variableCombMzDeltaFilter")
public class VariableCombMzDeltaFilter implements Filter<SpectrumImpl> {

    private double intensityThreshold;
    /**
     * The consecutive m/z delta values to filter for.
     */
    private double[] mzDeltaFilterValues;
    @Autowired
    private SpectrumBinner spectrumBinner;
    
    public void init(double intensityThreshold, double[] mzDeltaFilterValues) {
        this.intensityThreshold = intensityThreshold;
        this.mzDeltaFilterValues = mzDeltaFilterValues;
    }

    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        //@todo check if the max consecutive bins makes sense with the bin ceiling value
        //else we might get in trouble with the range
        boolean passesFilter = false;
        
        //get appropriate values for floor and ceiling
        double floor = mzDeltaFilterValues[0] - (BinParams.BIN_SIZE.getValue() * 2);
        double ceiling = getArraySum(mzDeltaFilterValues) + (BinParams.BIN_SIZE.getValue() * 2);
        Map<Double, TreeMap<Double, PeakBin>> peakBinsMap = spectrumBinner.getPeakBinsMap(spectrum, floor, ceiling, BinParams.BIN_SIZE.getValue());
        //iterate over the peakBins map of each peak
        for (TreeMap<Double, PeakBin> peakBins : peakBinsMap.values()) {

            /**
             * look for one peak at the relevant m/z delta values with the other
             * peaks, contained in the peakBins map. Start counting the given
             * range of consecutive m/z delta values; break if the a certain m/z
             * delta value is not present (below the intensitythreshold).
             */
            int consecMzDeltas = 0;
            double currentMassDeltaValue = 0.0;
            for (int i = 0; i < mzDeltaFilterValues.length; i++) {
                //get the key based on the current m/z delta value
                currentMassDeltaValue += mzDeltaFilterValues[i];
                Double key = peakBins.floorKey(currentMassDeltaValue);
                if (key == null || peakBins.get(key).getHighestIntensity() < intensityThreshold) {
                    //no need to go on
                    break;
                }
                else{
                    consecMzDeltas++;
                }
            }
            if(consecMzDeltas == mzDeltaFilterValues.length){
                passesFilter = true;
                break;
            }
        }

        if (doInvert) {
            passesFilter = !passesFilter;
        }

        return passesFilter;
    }
    
    /**
     * Calculate the sum of all values of a double array.
     * 
     * @param array the double array
     * @return the sum of the array values
     */
    private double getArraySum(double[] array){
        double sum = 0.0;
        for(int i = 0; i < array.length; i++){
            sum += array[i];
        }
        
        return sum;
    }
}
