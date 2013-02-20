package com.compomics.spectrawl.bin;

import com.compomics.spectrawl.model.Experiment;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 14/02/12
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */
public interface ExperimentBinner {

    /**
     * Fills the experiment bins
     *
     * @param experiment the experiment
     */
    void binExperiment(Experiment experiment);

    /**
     * Fills the experiment bins specified in binFloors
     *
     * @param experiment
     * @param binFloors the bin floors of the bins that need to be filled
     */
    void binExperiment(Experiment experiment, Set<Double> binFloors);
}
