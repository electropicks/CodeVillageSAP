package org.codevillage;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import java.awt.Rectangle;
import java.awt.Robot;

@Log4j2
@AllArgsConstructor
public class VillageSaver {
    private final String villageName;
    private final JFrame frame;
    private final Canvas canvas; // The canvas to capture

    public void saveVillage() {
        try {
            // Create a high-resolution BufferedImage
            int width = 1920;
            int height = 1080;
            BufferedImage highResImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = highResImage.createGraphics();

            canvas.drawContent(g2d);
            log.info("Canvas drawn: {}", canvas);

            // Dispose the graphics object
            g2d.dispose();

            // Save the high-resolution image
            String fileName = villageName + ".jpeg";
            File file = new File(fileName);
            ImageIO.write(highResImage, "JPEG", file);
            assert file.exists();

            JOptionPane.showMessageDialog(frame, "Village saved in high resolution as " + fileName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error saving village: " + e.getMessage());
        }
    }
}