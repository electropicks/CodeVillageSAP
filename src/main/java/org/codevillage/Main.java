package org.codevillage;

import org.codevillage.fetching.DataFetcher;
import org.codevillage.fetching.GithubDataFetcher;
import org.codevillage.fetching.LocalDataFetcher;
import org.codevillage.fetching.SVNDataFetcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main extends JFrame {
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

    // top panel components
    JLabel linkLabel = new JLabel("Link:");
    JLabel targetPathLabel = new JLabel("Target Path:");
    JLabel dataTypeLabel = new JLabel("Select Data Type:");
    JTextField linkTextField = new JTextField(20);
    linkTextField.setText("https://github.com/electropicks/CodeVillageSAP.git");
    JTextField targetPathTextField = new JTextField(20);
    targetPathTextField.setText("./temp");

    String[] dataTypes = { "GitHub", "Subversion", "Local Drive" };
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
        ArrayList<JavaEntity> entities = sourceCodeParser.parseSourceFiles(directory);
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

    saveVillageButton.addActionListener(e -> {
      villageSaver.saveVillage();
    });

    JButton openGraphButton = new JButton("Open Graph Window");
    topPanel.add(openGraphButton);

    openGraphButton.addActionListener(e -> {
      GraphWindow graphWindow = new GraphWindow();
      graphWindow.openGraphWindow();
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
