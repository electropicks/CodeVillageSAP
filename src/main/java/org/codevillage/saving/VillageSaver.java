package org.codevillage.saving;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.codevillage.Canvas;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


@Log4j2
@AllArgsConstructor
public class VillageSaver implements ImageSaver {
    private final String villageName;
    private final JFrame frame;
    private final Canvas canvas; // The canvas to capture

    @Override
    public void saveImage() {
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
            String fileName = villageName + ".png";
            File file = new File(fileName);

            ImageIO.write(highResImage, "PNG", file);
            boolean writeSuccess = ImageIO.write(highResImage, "PNG", file);
            log.debug("Write successful: {}", writeSuccess);

            log.info("Village saved in high resolution at {}", file.getAbsolutePath());
            if (!file.exists()) {
                log.error("File not found after saving");
            }

            JOptionPane.showMessageDialog(frame, "Village saved in high resolution as " + fileName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error saving village: " + e.getMessage());
        }
    }
}