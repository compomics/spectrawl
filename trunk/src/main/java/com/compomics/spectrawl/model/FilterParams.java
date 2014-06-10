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
public enum FilterParams {

    WINSOR_CONSTANT(PropertiesConfigurationHolder.getInstance().getDouble("WINSOR.CONSTANT")),
    WINSOR_CONVERGENCE_CRITERION(PropertiesConfigurationHolder.getInstance().getDouble("WINSOR.CONVERGENCE_CRITERION")),
    WINSOR_OUTLIER_LIMIT(PropertiesConfigurationHolder.getInstance().getDouble("WINSOR.OUTLIER_LIMIT")),
    WINSOR_MEAN_RATIO_THRESHOLD(PropertiesConfigurationHolder.getInstance().getDouble("WINSOR.MEAN_RATIO_THRESHOLD")),
    WINSOR_DENSITY_THRESHOLD(PropertiesConfigurationHolder.getInstance().getDouble("WINSOR.DENSITY_THRESHOLD"));

    private double value;

    private FilterParams(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
