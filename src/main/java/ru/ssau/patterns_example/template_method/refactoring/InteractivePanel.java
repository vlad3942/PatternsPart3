package ru.ssau.patterns_example.template_method.refactoring;

import ru.ssau.patterns_example.template_method.refactoring.shapes.AbstractShape;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class InteractivePanel extends JPanel {

    private Set<AbstractShape> shapes = new HashSet<>();
    private Dimension dimension = new Dimension(400, 400);

    public InteractivePanel() {
        setBackground(Color.lightGray);
        setPreferredSize(dimension);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        shapes.forEach(e -> e.draw(g));
    }

    public void addShape(AbstractShape shape) {
        shapes.add(shape);
        repaint();
    }

    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}
