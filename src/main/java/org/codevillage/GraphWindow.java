package org.codevillage;

import lombok.Getter;
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
import java.util.List;

public class GraphWindow {
    @Getter
    private final JFrame graphFrame;
    @Getter
    private final ChartPanel chartPanel;

    public GraphWindow(AICalculator aiCalculator) {
        graphFrame = new JFrame("Graph Window");
        graphFrame.setSize(600, 400);

        XYSeries series = new XYSeries("Java Classes");

        List<Double> instabilityList = aiCalculator.getInstabilityList();
        List<Double> abstractnessList = aiCalculator.getAbstractnessList();

        for (int i = 0; i < abstractnessList.size(); i++) {
            double x = instabilityList.get(i);
            double y = abstractnessList.get(i);
            series.add(x, y);
        }

        XYDataset dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Java Class Metrics",
                "Instability (I Axis)",
                "Abstractness (A Axis)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chartPanel = new ChartPanel(chart);
        graphFrame.getContentPane().add(chartPanel, BorderLayout.CENTER);

        graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        graphFrame.setVisible(true);

        // Add save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            // Save image
            SAPGraphSaver sapGraphSaver = new SAPGraphSaver("SAP_Graph", graphFrame, chartPanel);
            sapGraphSaver.saveImage();
        });
        graphFrame.getContentPane().add(saveButton, BorderLayout.SOUTH);
    }
}
