/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.controller;

import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.view.ExperimentBinsPanel;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
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
    DefaultCategoryDataset categoryDataset;
    //view
    private ExperimentBinsPanel experimentBinsPanel;
    //parent controller
    private SpectrawlController spectrawlController;

    public ExperimentBinsController(SpectrawlController spectrawlController) {
        experimentBinsPanel = new ExperimentBinsPanel();

        this.spectrawlController = spectrawlController;

        initPanel();
    }

    public ExperimentBinsPanel getExperimentBinsPanel() {
        return experimentBinsPanel;
    }

    public void viewExerimentBins(Experiment experiment) {
        LOGGER.debug("initialize bins chart of experiment");
        
        //make new data set
        categoryDataset = new DefaultCategoryDataset();
        
        //add values
        for (Double floorBin : experiment.getBins().keySet()) {
            categoryDataset.addValue(experiment.getBins().get(floorBin).getHighestIntensityQuantiles().getPercentile_50(), "highestIntensity", floorBin.toString());
            categoryDataset.addValue(experiment.getBins().get(floorBin).getIntensitySumQuantiles().getPercentile_50(), "intensitySum", floorBin.toString());
            //categoryDataset.addValue(experiment.getBins().get(floorBin).getPeakCountQuantiles().getPercentile_50(), "peakCount", floorBin.toString());
        }

        JFreeChart chart = ChartFactory.createBarChart("title", "bin", "relative intensity", categoryDataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);

        ChartPanel chartPanel = new ChartPanel(chart);
        //chartPanel.setPreferredSize(new Dimension(1000, 1000));

        LOGGER.debug("finished initializing bins chart of experiment");
    }

    private void initPanel() {
    }
}
