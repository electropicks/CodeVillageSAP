package org.codevillage;

import org.codevillage.saving.SAPGraphSaver;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class GraphWindow {
    public void openGraphWindow() {
        JFrame graphFrame = new JFrame("Graph Window");
        graphFrame.setSize(600, 400);

        XYSeries series = new XYSeries("Sample Data");
        series.add(1, 5);
        series.add(2, 8);
        series.add(3, 12);
        series.add(4, 6);

        XYDataset dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Sample Chart",
                "A Axis",
                "I Axis",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        graphFrame.getContentPane().add(chartPanel, BorderLayout.CENTER);

        graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        graphFrame.setVisible(true);
        // add save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            // save image
             SAPGraphSaver sapGraphSaver = new SAPGraphSaver("SAP_Graph", graphFrame, chartPanel);
             sapGraphSaver.saveImage();
        });
        graphFrame.getContentPane().add(saveButton, BorderLayout.SOUTH);
    }
}
