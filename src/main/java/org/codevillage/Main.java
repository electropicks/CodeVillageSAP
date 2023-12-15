package org.codevillage;

import lombok.extern.log4j.Log4j2;
import org.codevillage.fetching.DataFetcher;
import org.codevillage.fetching.GithubDataFetcher;
import org.codevillage.fetching.LocalDataFetcher;
import org.codevillage.fetching.SVNDataFetcher;
import org.codevillage.saving.SAPGraphSaver;
import org.codevillage.saving.VillageSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

@Log4j2
public class Main extends JFrame {
    private AICalculator aiCalculator;  // Declare aiCalculator as an instance variable
    private ArrayList<JavaEntity> entities;  // Declare entities as an instance variable

    public static void main(String[] args) {
        Main window = new Main();
        window.setSize(1000, 1000);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("509 Project 1");
    }

    Main() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new GridLayout(5, 2));
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        Canvas canvas = new Canvas();

        VillageSaver villageSaver = new VillageSaver("CodeVillage", this, canvas);

        JLabel linkLabel = new JLabel("Link:");
        JLabel targetPathLabel = new JLabel("Target Path:");
        JLabel dataTypeLabel = new JLabel("Select Data Type:");
        JTextField linkTextField = new JTextField(20);
        linkTextField.setText("https://github.com/Fall23csc509/CodeVillage.git");
        JTextField targetPathTextField = new JTextField(20);
        targetPathTextField.setText("./temp");

        String[] dataTypes = {"GitHub", "Subversion", "Local Drive"};
        JComboBox<String> dataTypeDropdown = new JComboBox<>(dataTypes);
        JButton submitButton = new JButton("Submit");

        DataFetcher[] fetch = {null};
        fetch[0] = new GithubDataFetcher();

        submitButton.addActionListener(e -> {
            String selectedDataType = (String) dataTypeDropdown.getSelectedItem();
            String link = linkTextField.getText();
            String targetPath = targetPathTextField.getText();
            JOptionPane.showMessageDialog(Main.this,
                    "Selected Data Type: " + selectedDataType + "\nLink: " + link + "\nTarget Path: " + targetPath);
            fetch[0].downloadPackage(link, targetPath);
            File directory = new File(targetPath);
            SourceCodeParser sourceCodeParser = new SourceCodeParser();
            try {
                entities = sourceCodeParser.parseSourceFiles(directory);

                aiCalculator = new AICalculator();
                if (!entities.isEmpty() && entities.get(0) instanceof JavaClass) {
                    ArrayList<JavaClass> javaClasses = new ArrayList<>();
                    for (JavaEntity entity : entities) {
                        if (entity instanceof JavaClass) {
                            javaClasses.add((JavaClass) entity);
                        }
                    }
                    aiCalculator.calculateConnections(javaClasses);
                }
                NeighborhoodGroupingLink neighborhoodGroupingLink = new NeighborhoodGroupingLink(
                        new InterfaceInsertLink(
                                new ShapeInitLink(
                                        new ShapesRelativePositioningLink(
                                                new NeighborhoodDimensionsLink(
                                                        new NeighborhoodAbsolutePositioningLink(
                                                                new ShapesAbsolutePositioningLink(
                                                                        new PutShapesChainEnd()
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );
                neighborhoodGroupingLink.position(entities);
                log.info("Created {} entities", entities.size());

            } catch (IOException ioError) {
                ioError.printStackTrace();
                // Delete target path directory
            }
            try {
                Utils.deleteDirectory(Path.of(targetPath));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        topPanel.add(linkLabel);
        topPanel.add(linkTextField);
        topPanel.add(targetPathLabel);
        topPanel.add(targetPathTextField);
        topPanel.add(dataTypeLabel);
        topPanel.add(dataTypeDropdown);
        topPanel.add(submitButton);

        CanvasData data = CanvasData.getInstance();
        data.addPropertyChangeListener(canvas);
        canvas.setBackground(Color.LIGHT_GRAY);

        Camera cameraInstance = Camera.getInstance();
        addKeyListener(cameraInstance.getKeyListener());

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(canvas, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        JButton saveVillageButton = new JButton("Save Village");
        bottomPanel.add(saveVillageButton);

        saveVillageButton.addActionListener(s -> villageSaver.saveImage());

        JButton openGraphButton = new JButton("Open Graph Window");
        topPanel.add(openGraphButton);

        openGraphButton.addActionListener(o -> {
            if (aiCalculator != null && entities != null) {
                GraphWindow graphWindow = new GraphWindow(aiCalculator);
                SAPGraphSaver sapGraphSaver = new SAPGraphSaver("SAP_Graph", graphWindow.getGraphFrame(), graphWindow.getChartPanel());
                sapGraphSaver.saveImage();
            }
        });

        dataTypeDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedDataType = (String) e.getItem();
                if ("GitHub".equals(selectedDataType)) {
                    fetch[0] = new GithubDataFetcher();
                } else if ("Subversion".equals(selectedDataType)) {
                    fetch[0] = new SVNDataFetcher();
                } else {
                    fetch[0] = new LocalDataFetcher();
                }
            }
        });
    }
}
