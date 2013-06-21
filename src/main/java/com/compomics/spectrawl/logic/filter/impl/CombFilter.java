/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.compomics.spectrawl.logic.filter.impl;

import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.model.SpectrumImpl;
import java.util.ArrayList;

/**
 * Filters which looks for a combination of subsequent delta masses
 *
 * @author marc
 */
public class CombFilter implements Filter<SpectrumImpl> {

    /**
     * The m/z intervals between the teeth of the comb
     */
    private ArrayList<Double> deltaMasses = new ArrayList<Double>();
    
    /**
     * Constructs a comb with the given m/z intervals
     * @param deltaMasses The m/z intervals between the teeth of the comb 
     */
    public CombFilter(ArrayList<Double> deltaMasses) {
        this.deltaMasses.addAll(deltaMasses);
    }
    
    /**
     * Constructs an empty comb
     */
    public CombFilter() {
        
    }
    
    /**
     * Adds a tooth to the comb
     * @param mz m/z distance with the last tooth
     */
    public void addTooth(double mz) {
        deltaMasses.add(mz);
    }
    
    @Override
    public boolean passesFilter(SpectrumImpl t, boolean doInvert) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
