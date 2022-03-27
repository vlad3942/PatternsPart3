package ru.ssau.patterns_example.template_method.legacy;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public abstract class ShapeMover implements Runnable {

    private final int min;
    private final int  max;

    private long time = 3000L;

    protected int xStart = 0;
    protected int yStart = 0;
    protected int xFinish = 180;
    protected int yFinish = 37;

    private Pane canvas;

    public ShapeMover(
            final int min,
            final int max,
            final Pane canvas
    ) {
        this.canvas = canvas;
        this.min = min;
        this.max = max;
    }

    public abstract Shape initShape();

    @Override
    public void run() {
        Shape shape = initShape();
        //Создание перехода
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(time), shape);

        translateTransition.setByX(xFinish);
        translateTransition.setByY(yFinish);

        //Playing the animation
        translateTransition.play();

        canvas.getChildren().addAll(shape/*, rectangle*/);

        while (true) {
            this.checker(shape);
        }
    }

    private void checker(Shape shape) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        calc();
        TranslateTransition tt = new TranslateTransition(Duration.millis(time), shape);
        tt.setByX(xFinish - xStart);
        tt.setByY(yFinish - yStart);
        tt.play();
    }

    public void calc() {
        int x_old = xStart;
        int y_old = yStart;
        int x = xFinish;
        int y = yFinish;
        if (x - x_old == 0) {
            int y_tmp = yStart;
            yStart = yFinish;
            yFinish = y_tmp;
            return;
        }
        if (y - y_old == 0) {
            int x_tmp = xStart;
            xStart = xFinish;
            xFinish = x_tmp;
            return;
        }
        //Получаем отражённый отрезок:
        int y_old_1 = -y_old + 2 * y;

        double a = 1.0d * (y - y_old_1) / (x - x_old);
        double b = y_old_1 - (1.0d * x_old * (y - y_old_1) / (x - x_old));

        //Пытаемся найти пересечения с рамкой:
        double test = a * min + b;
        int val = (int) Math.round(test);
        if (val <= max && val >= min) {
            if (y != val && x != min) {
                swap(min, val);
                return;
            }
        }
        test = a * max + b;
        val = (int) Math.round(test);
        if (val <= max && val >= min) {
            if (y != val && x != max) {
                swap(max, val);
                return;
            }
        }
        test = (min - b) / a;
        val = (int) Math.round(test);
        if (val <= max && val >= min) {
            if (y != min && x != val) {
                swap(val, min);
                return;
            }
        }
        test = (max - b) / a;
        val = (int) Math.round(test);
        if (val <= max && val >= min) {
            if (y != max && x != val) {
                swap(val, max);
            }
        }
    }

    private void swap(int p1, int p2) {
        xStart = xFinish;
        xFinish = p1;
        yStart = yFinish;
        yFinish = p2;
    }
}
