package org.codevillage;

import lombok.AllArgsConstructor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@AllArgsConstructor
public class VillageSaver {
    private final String villageName;
    private final JFrame frame;

    public void saveVillage() {
        try {
            // Determine the bounds of the window
            Rectangle rect = frame.getBounds();

            // Capture the screen area
            Robot robot = new Robot(frame.getGraphicsConfiguration().getDevice());
            BufferedImage image = robot.createScreenCapture(rect);

            // Save the image as a JPEG file
            String fileName = villageName + ".jpeg";
            ImageIO.write(image, "JPEG", new File(fileName));

            JOptionPane.showMessageDialog(frame, "Village saved as " + villageName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error saving village: " + e.getMessage());
        }
    }
}