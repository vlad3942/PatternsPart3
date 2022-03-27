package ru.ssau.patterns_example.template_method.refactoring.shapes;

import ru.ssau.patterns_example.template_method.refactoring.InteractivePanel;

import java.awt.*;
import java.awt.geom.GeneralPath;

public abstract class AbstractShape implements Runnable {

    private double x;
    private double y;
    private double dx;
    private double dy;
    private double d = 10;

    //
    private static final int circleR = 10;
    private int min = 0;
    private int max = 500 - 2 * circleR;

    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    private int xStart = 0;
    private int yStart = 0;
    private int xFinish = max;
    private int yFinish = 137;
    //

    private InteractivePanel panel;

    public AbstractShape(InteractivePanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {

        panel.addShape(this);
        initStartFinish();
        calcD();

        while (true) {
            if (x == xFinish && y == yFinish) {
                calcMod();
                calcD();
            }
            updateShape();

            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.print(ex.getMessage());
            }

            moveXY();
        }
    }

    protected double getX() {
        return x;
    }

    protected double getY() {
        return y;
    }

    public abstract void draw(Graphics gr);

    protected void updateShape() {
        panel.revalidate();
        panel.repaint();
    }

    private void initStartFinish() {
        xStart = minX;
        yStart = minY;
        xFinish = maxX;
        yFinish = 137;
    }

    private void moveXY() {
        if (dx < 0) {
            if (x + dx < xFinish) {
                x = xFinish;
            } else {
                x += dx;
            }
        } else {
            if (x + dx > xFinish) {
                x = xFinish;
            } else {
                x += dx;
            }
        }
        if (dy < 0) {
            if (y + dy < yFinish) {
                y = yFinish;
            } else {
                y += dy;
            }
        } else {
            if (y + dy > yFinish) {
                y = yFinish;
            } else {
                y += dy;
            }
        }
    }

    private void calcD() {
        if (xFinish - xStart == 0) {
            dx = 0;
            if (yFinish > yStart) {
                dy = d;
            } else {
                dy = -d;
            }
            return;
        }
        if (yFinish - yStart == 0) {
            if (xFinish > xStart) {
                dx = d;
            } else {
                dx = -d;
            }
            dy = 0;
            return;
        }
        double k = Math.abs(yFinish - yStart) * 1.0d / Math.abs(xFinish - xStart);
        dx = Math.sqrt(d * d / (1 + k * k));
        dy = k * dx;
        if (xFinish < xStart) {
            dx = -dx;
        }
        if (yFinish < yStart) {
            dy = -dy;
        }
    }

    private void calc() {
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

    private void calcMod() {
        if (checkAngle()) {
            return;
        }
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
        double test = a * minX + b;
        int val = (int) Math.round(test);
        if (val <= maxY && val >= minY) {
            if (y != val && x != minX) {
                swap(minX, val);
                return;
            }
        }
        test = a * maxX + b;
        val = (int) Math.round(test);
        if (val <= maxY && val >= minY) {
            if (y != val && x != maxX) {
                swap(maxX, val);
                return;
            }
        }
        test = (minY - b) / a;
        val = (int) Math.round(test);
        if (val <= maxX && val >= minX) {
            if (y != minY && x != val) {
                swap(val, minY);
                return;
            }
        }
        test = (maxY - b) / a;
        val = (int) Math.round(test);
        if (val <= maxX && val >= minX) {
            if (y != maxY && x != val) {
                swap(val, maxY);
            }
        }
    }

    private boolean checkAngle() {
        if (
                x == minX && y == minY
                || x == maxX && y == maxY
                || x == maxX && y == minY
                || x == minX && y == maxY
        ) {
            int tmp = xStart;
            xStart = xFinish;
            xFinish = tmp;
            tmp = yStart;
            yStart = yFinish;
            yFinish = tmp;
            return true;
        }
        return false;
    }

    private void swap(int p1, int p2) {
        xStart = xFinish;
        xFinish = p1;
        yStart = yFinish;
        yFinish = p2;
    }

    protected void setXStart(int xStart) {
        this.xStart = xStart;
    }

    protected void setYStart(int yStart) {
        this.yStart = yStart;
    }

    protected void setXFinish(int xFinish) {
        this.xFinish = xFinish;
    }

    protected void setYFinish(int yFinish) {
        this.yFinish = yFinish;
    }

    protected void setMinX(int minX) {
        this.minX = minX;
    }

    protected void setMinY(int minY) {
        this.minY = minY;
    }

    protected void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    protected void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    protected static GeneralPath createShapeByPoints(int[] yPoints, int[] xPoints, int x, int y) {
        GeneralPath shape = new GeneralPath();
        shape.moveTo((xPoints[0] + x), (yPoints[0] + y));
        for (int i = 1; i < xPoints.length; i++) {
            shape.lineTo((xPoints[i] + x), (yPoints[i] + y));
        }
        shape.closePath();
        return shape;
    }
}
