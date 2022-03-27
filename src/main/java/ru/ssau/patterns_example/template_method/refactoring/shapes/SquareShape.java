package ru.ssau.patterns_example.template_method.refactoring.shapes;

import ru.ssau.patterns_example.template_method.refactoring.InteractivePanel;

import java.awt.*;

public class SquareShape extends AbstractShape{

    private int size;

    public SquareShape(InteractivePanel panel, int size) {
        super(panel);
        this.size = size;
        setMinX(0);
        setMinY(0);
        setMaxX(panel.getDimension().width - size);
        setMaxY(panel.getDimension().height - size);
    }

    @Override
    public void draw(Graphics gr) {
        int[] xPoints = {0, size, size, 0};
        int[] yPoints = {0, 0, size, size};

        gr.setColor(Color.BLUE);
        ((Graphics2D) gr).fill(createShapeByPoints(yPoints, xPoints, (int) getX(), (int) getY()));
    }
}
