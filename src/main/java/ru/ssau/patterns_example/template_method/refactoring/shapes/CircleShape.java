package ru.ssau.patterns_example.template_method.refactoring.shapes;

import ru.ssau.patterns_example.template_method.refactoring.InteractivePanel;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircleShape extends AbstractShape {

    private double r;

    public CircleShape(InteractivePanel panel, final double r) {
        super(panel);
        int height = panel.getDimension().height;
        int wight = panel.getDimension().width;
        int rInt = (int) Math.round(r);
        setMinX(0);
        setMinY(0);
        setMaxX(wight - 2 * rInt);
        setMaxY(height - 2 * rInt);
        this.r = r;
    }

    @Override
    public void draw(Graphics gr) {
        gr.setColor(Color.RED);
        ((Graphics2D) gr).fill(new Ellipse2D.Double(getX(), getY(), 2 * r, 2 * r));
    }
}
