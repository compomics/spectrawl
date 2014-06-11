/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.spectrawl.gui.controller;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import com.compomics.spectrawl.gui.SpectrumPanelFactory;
import com.compomics.spectrawl.gui.SpectrumTableFormat;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.gui.view.ResultPanel;
import com.compomics.spectrawl.model.SpectrumComparator;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.spectrawl.util.GuiUtils;
import com.compomics.util.gui.spectrum.SpectrumPanel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
 * @author Niels Hulstaert
 */
public class ResultController {

    private static final Logger LOGGER = Logger.getLogger(ResultController.class);
    //model
    private EventList<SpectrumImpl> spectrumEventList;
    private SortedList<SpectrumImpl> sortedSpectrumList;
    //view
    private ChartPanel intensitiesChartPanel;
//    private ChartPanel countChartPanel;
    private ResultPanel resultPanel;

    /**
     * Get the view.
     *
     * @return the view
     */
    public ResultPanel getResultPanel() {
        return resultPanel;
    }

    /**
     * Init the controller.
     *
     */
    public void init() {
        //init the panel
        resultPanel = new ResultPanel();
        intensitiesChartPanel = new ChartPanel(null);
        intensitiesChartPanel.setOpaque(false);
//        countChartPanel = new ChartPanel(null);
//        countChartPanel.setOpaque(false);

        spectrumEventList = new BasicEventList<>();
        sortedSpectrumList = new SortedList<>(spectrumEventList, new SpectrumComparator());
        resultPanel.getSpectrumTable().setModel(new EventTableModel(sortedSpectrumList, new SpectrumTableFormat()));
        resultPanel.getSpectrumTable().setSelectionModel(new EventSelectionModel(sortedSpectrumList));

        //set column widths
        resultPanel.getSpectrumTable().getColumnModel().getColumn(SpectrumTableFormat.SPECTRUM_ID).setPreferredWidth(20);
        resultPanel.getSpectrumTable().getColumnModel().getColumn(SpectrumTableFormat.FILE_NAME).setPreferredWidth(100);
        resultPanel.getSpectrumTable().getColumnModel().getColumn(SpectrumTableFormat.PRECURSOR_MZ).setPreferredWidth(20);
        resultPanel.getSpectrumTable().getColumnModel().getColumn(SpectrumTableFormat.CHARGE).setPreferredWidth(10);

        //use MULTIPLE_COLUMN_MOUSE to allow sorting by multiple columns
        TableComparatorChooser tableSorter = TableComparatorChooser.install(
                resultPanel.getSpectrumTable(), sortedSpectrumList, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

        //add listeners
        resultPanel.getSpectrumTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    if (resultPanel.getSpectrumTable().getSelectedRow() != -1) {
                        SpectrumImpl spectrum = sortedSpectrumList.get(resultPanel.getSpectrumTable().getSelectedRow());

                        SpectrumPanel spectrumPanel = SpectrumPanelFactory.getSpectrumPanel(spectrum);

                        addSpectrumPanel(spectrumPanel);
                    }
                }
            }
        });

        //add chartPanel                  
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        resultPanel.getIntensityChartParentPanel().add(intensitiesChartPanel, gridBagConstraints);
//        resultPanel.getCountChartParentPanel().add(countChartPanel, gridBagConstraints);
    }

    /**
     * Update the result panel
     *
     * @param experiment the experiment
     */
    public void updateResultPanel(Experiment experiment) {
        LOGGER.debug("initializing bins charts of experiment");

        //create new data set
        DefaultCategoryDataset intensitiesCategoryDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset countCategoryDataset = new DefaultCategoryDataset();

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
        //set labels invisible for clarity
        int counter = 0;
        int divider = (experiment.getBins().size() > 100) ? 10 : 5;
        for (Double floorBin : experiment.getBins().keySet()) {
            if (counter % divider != 0) {
                xAxis.setTickLabelPaint(floorBin.toString(), new Color(0, 0, 0, 0));
            }
            counter++;
        }
        intensitiesPlot.setBackgroundPaint(Color.WHITE);
        intensitiesPlot.setOutlineVisible(false);
        GuiUtils.setShadowVisible(intensitiesChart, false);

        //create count chart
//        JFreeChart countChart = ChartFactory.createBarChart("experiment bins", "bin", "count", countCategoryDataset, PlotOrientation.VERTICAL, true, true, false);
//        CategoryPlot countPlot = (CategoryPlot) countChart.getPlot();
//        xAxis = (CategoryAxis) countPlot.getDomainAxis();
//        xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
//        countChart.getPlot().setBackgroundPaint(Color.WHITE);
//        countChart.getPlot().setOutlineVisible(false);
//        GuiUtils.setShadowVisible(countChart, false);
        //add charts to panels
        intensitiesChartPanel.setChart(intensitiesChart);
//        countChartPanel.setChart(countChart);

        //set spectrum event list
        spectrumEventList.clear();
        spectrumEventList.addAll(experiment.getSpectra());
        addSpectrumPanel(null);

        LOGGER.debug("finished initializing bins charts of experiment");
    }

    /**
     * Clear the result section
     */
    public void clear() {
        resultPanel.getExperimentInfoLabel().setText("");
        intensitiesChartPanel.setChart(null);
//        countChartPanel.setChart(null);
        addSpectrumPanel(null);
        spectrumEventList.clear();
    }

    /**
     * Add the spectrum panel;
     *
     * @param spectrumPanel the spectrum panel
     */
    private void addSpectrumPanel(SpectrumPanel spectrumPanel) {
        //remove spectrum panel if already present
        if (resultPanel.getSpectrumDetailPanel().getComponentCount() != 0) {
            resultPanel.getSpectrumDetailPanel().remove(0);
        }

        if (spectrumPanel != null) {
            //add the spectrum panel to the identifications detail panel
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;

            resultPanel.getSpectrumDetailPanel().add(spectrumPanel, gridBagConstraints);
        }

        resultPanel.getSpectrumDetailPanel().validate();
        resultPanel.getSpectrumDetailPanel().repaint();
    }

    /**
     * Show experiment info in the experiment info label.
     *
     * @param message the info message
     */
    public void showExperimentInfo(String message) {
        resultPanel.getExperimentInfoLabel().setText(message);
    }
}
