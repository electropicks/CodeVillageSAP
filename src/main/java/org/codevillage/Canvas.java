package org.codevillage;

import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;


@Log4j2
public class Canvas extends JPanel implements PropertyChangeListener {
    public Canvas() {
        super(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawContent((Graphics2D) g);
    }

    public void drawContent(Graphics2D g) {
        List<Shape> newShapes = CanvasData.getInstance().getShapes();

        for (Shape shape : newShapes) {
            shape.draw(g);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("shapes".equals(evt.getPropertyName())) {
            List<Shape> newShapes = (List<Shape>) evt.getNewValue();
            log.info("Canvas has been updated and now has {} shapes.", newShapes.size());
            repaint();
        }
    }

}
