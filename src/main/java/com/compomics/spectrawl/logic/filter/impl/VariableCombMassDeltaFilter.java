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
@Component("variableCombMassDeltaFilter")
public class VariableCombMassDeltaFilter implements Filter<SpectrumImpl> {

    private double intensityThreshold;
    /**
     * The consecutive mass delta values to filter for.
     */
    private double[] massDeltaFilterValues;
    @Autowired
    private SpectrumBinner spectrumBinner;
    
    public void init(double intensityThreshold, double[] massDeltaFilterValues) {
        this.intensityThreshold = intensityThreshold;
        this.massDeltaFilterValues = massDeltaFilterValues;
    }

    @Override
    public boolean passesFilter(SpectrumImpl spectrum, boolean doInvert) {
        //@todo check if the max consecutive bins makes sense with the bin ceiling value
        //else we might get in trouble with the range
        boolean passesFilter = false;
        
        //get appropriate values for floor and ceiling
        double floor = massDeltaFilterValues[0] - (BinParams.BIN_SIZE.getValue() * 2);
        double ceiling = getArraySum(massDeltaFilterValues) + (BinParams.BIN_SIZE.getValue() * 2);
        Map<Double, TreeMap<Double, PeakBin>> peakBinsMap = spectrumBinner.getPeakBinsMap(spectrum, floor, ceiling, BinParams.BIN_SIZE.getValue());
        //iterate over the peakBins map of each peak
        for (TreeMap<Double, PeakBin> peakBins : peakBinsMap.values()) {

            /**
             * look for one peak at the relevant M/Z delta values with the other
             * peaks, contained in the peakBins map. Start counting the given
             * range of consecutive M/Z delta values; break if the a certain M/Z
             * delta value is not present (below the intensitythreshold).
             */
            int consecMassDeltas = 0;
            double currentMassDeltaValue = 0.0;
            for (int i = 0; i < massDeltaFilterValues.length; i++) {
                //get the key based on the current mass delta value
                currentMassDeltaValue += massDeltaFilterValues[i];
                Double key = peakBins.floorKey(currentMassDeltaValue);
                if (key != null && peakBins.get(key).getIntensitySum() < intensityThreshold) {
                    //no need to go on
                    break;
                }
                else{
                    consecMassDeltas++;
                }
            }
            if(consecMassDeltas == massDeltaFilterValues.length){
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
