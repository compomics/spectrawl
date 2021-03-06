/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.model;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;

/**
 *
 * @author Niels Hulstaert
 */
public enum BinParams {
    
    BINS_FLOOR(PropertiesConfigurationHolder.getInstance().getDouble("BINS_FLOOR")),
    BINS_CEILING(PropertiesConfigurationHolder.getInstance().getDouble("BINS_CEILING")),
    BIN_SIZE(PropertiesConfigurationHolder.getInstance().getDouble("BIN_SIZE"));
    
    private double value;
    
    private BinParams(double value){
        this.value = value;
    }
    
    public double getValue(){
        return value;
    }
    
    public void setValue(double value){
        this.value = value;
    }
        
}
