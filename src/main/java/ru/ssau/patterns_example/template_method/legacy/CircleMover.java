package ru.ssau.patterns_example.template_method.legacy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CircleMover extends ShapeMover {

    private Circle circle;

    public CircleMover(
            final int min,
            final int max,
            final Pane canvas,
            final int circleR
    ) {
        super(min, max, canvas);
        circle = new Circle(circleR);
        circle.setCenterX(xStart + circleR);
        circle.setCenterY(yStart + circleR);
        circle.setFill(Color.AQUAMARINE);
        circle.setStrokeWidth(5);
    }

    @Override
    public Shape initShape() {
        return circle;
    }
}
