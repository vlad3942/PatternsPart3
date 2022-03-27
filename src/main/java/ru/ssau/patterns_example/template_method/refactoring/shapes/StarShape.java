package ru.ssau.patterns_example.template_method.refactoring.shapes;

import ru.ssau.patterns_example.template_method.refactoring.InteractivePanel;

import java.awt.*;

public class StarShape extends AbstractShape {

    private final int[] xPoints = {9, 15, 0, 18, 3};
    private final int[] yPoints = {0, 18, 6, 6, 18};

    public StarShape(InteractivePanel panel) {
        super(panel);
        int height = panel.getDimension().height;
        int wight = panel.getDimension().width;
        setMinX(0);
        setMinY(0);
        setMaxX(wight - 18);
        setMaxY(height - 18);
    }

    @Override
    public void draw(Graphics gr) {
        gr.setColor(Color.YELLOW);
        ((Graphics2D) gr).fill(createShapeByPoints(yPoints, xPoints, (int) getX(), (int) getY()));
    }
}
