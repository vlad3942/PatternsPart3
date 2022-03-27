package ru.ssau.patterns_example.template_method.refactoring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class ShapeThread extends JComponent implements Runnable, ActionListener {

    private double scale;
    private Color color;
    private Timer timer;
    public double x;
    public double y;
    public double dx;
    public double dy;
    public double d = 10;

    //
    private static final int circleR = 10;
    private static final int min = 0;
    private static final int max = 500 - 2 * circleR;

    public int xStart = 0;
    public int yStart = 0;
    public int xFinish = max;
    public int yFinish = 137;
    //

    JPanel panel;

    public ShapeThread(JPanel panel, Color color) {
        panel.add(this);
        this.panel = panel;
        scale = 1.0;
        timer = new Timer(20, this);
        this.color = color;
        setPreferredSize(new Dimension(500, 500));
        x = xStart;
        y = yStart;
        calcD();
    }


    @Override
    public void run() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        panel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (x == xFinish && y == yFinish) {
            calc();
            calcD();
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        int width = 500;
        int height = 500;
        g.fillRect(0, 0, width, height);
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.scale(scale, scale);
        moveXY();
        g2d.fill(getShape());
    }

    Shape getShape() {
        return new Ellipse2D.Double(x, y, 20, 20);
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
