/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.controller;

import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.view.ExperimentBinsPanel;
import java.awt.GridBagConstraints;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author niels
 */
public class ExperimentBinsController {

    private static final Logger LOGGER = Logger.getLogger(ExperimentBinsController.class);
    //model
    DefaultCategoryDataset intensitiesCategoryDataset;
    DefaultCategoryDataset countCategoryDataset;
    //view
    private ChartPanel intensitiesChartPanel;
    private ChartPanel countChartPanel;
    private ExperimentBinsPanel experimentBinsPanel;
    //parent controller
    private SpectrawlController spectrawlController;
    
    /**
     * Constructor
     * 
     * @param spectrawlController the parent controller
     */
    public ExperimentBinsController(SpectrawlController spectrawlController) {
        intensitiesChartPanel = new ChartPanel(null);
        countChartPanel = new ChartPanel(null);
        experimentBinsPanel = new ExperimentBinsPanel();

        this.spectrawlController = spectrawlController;

        initPanel();
    }

    /**
     * Gets the view
     * 
     * @return the view
     */
    public ExperimentBinsPanel getExperimentBinsPanel() {
        return experimentBinsPanel;
    }

    /**
     * Shows the experiment bins a bar chart
     * 
     * @param experiment the experiment
     */
    public void viewExperimentBins(Experiment experiment) {
        LOGGER.debug("initializing bins charts of experiment");
        //create new data set
        intensitiesCategoryDataset = new DefaultCategoryDataset();
        countCategoryDataset = new DefaultCategoryDataset();

        //add intensity values
        for (Double floorBin : experiment.getBins().keySet()) {
            //add intensity values
            intensitiesCategoryDataset.addValue(experiment.getBins().get(floorBin).getHighestIntensityQuantiles().getPercentile_50(), "highestIntensity (50% quantile)", floorBin.toString());
            intensitiesCategoryDataset.addValue(experiment.getBins().get(floorBin).getIntensitySumQuantiles().getPercentile_50(), "intensitySum (50% quantile)", floorBin.toString());
            //add count values
            countCategoryDataset.addValue(experiment.getBins().get(floorBin).getPeakCountQuantiles().getPercentile_50(), "peakCount (50% quantile)", floorBin.toString());
            countCategoryDataset.addValue(experiment.getBins().get(floorBin).getPeakCountQuantiles().getMaximum(), "peakCount (max)", floorBin.toString());
        }
                        
        //create intensity chart
        JFreeChart intensitiesChart = ChartFactory.createBarChart("experiment bins", "bin", "relative intensity", intensitiesCategoryDataset, PlotOrientation.VERTICAL, true, true, false);        
        CategoryPlot intensitiesPlot = (CategoryPlot) intensitiesChart.getPlot();
        CategoryAxis xAxis = (CategoryAxis) intensitiesPlot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
        
        //create count chart
        JFreeChart countChart = ChartFactory.createBarChart("experiment bins", "bin", "count", countCategoryDataset, PlotOrientation.VERTICAL, true, true, false);        
        CategoryPlot countPlot = (CategoryPlot) countChart.getPlot();
        xAxis = (CategoryAxis) countPlot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
        
        //add charts to panels
        intensitiesChartPanel.setChart(intensitiesChart);
        countChartPanel.setChart(countChart);
        
        LOGGER.debug("finished initializing bins charts of experiment");
    }
    
    /**
     * Removes the current chart from the chart panel
     * and resets the info label
     * 
     */
    public void resetChartPanel(){
        experimentBinsPanel.getExperimentInfoLabel().setText("");
        intensitiesChartPanel.setChart(null);
        countChartPanel.setChart(null);
    }
    
    /**
     * Shows experiment info in the experiment info label
     * 
     * @param message the info message
     */
    public void showExperimentInfo(String message){
        experimentBinsPanel.getExperimentInfoLabel().setText(message);
    }
    
    /**
     * Inits the view
     * 
     */
    private void initPanel() {
        //add chartPanel                  
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        
        experimentBinsPanel.getIntensityChartParentPanel().add(intensitiesChartPanel, gridBagConstraints);
        experimentBinsPanel.getCountChartParentPanel().add(countChartPanel, gridBagConstraints);
    }
}
