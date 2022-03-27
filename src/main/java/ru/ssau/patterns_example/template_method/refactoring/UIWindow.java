package ru.ssau.patterns_example.template_method.refactoring;

import javafx.scene.chart.PieChart;
import ru.ssau.patterns_example.template_method.refactoring.shapes.CircleShape;
import ru.ssau.patterns_example.template_method.refactoring.shapes.SquareShape;
import ru.ssau.patterns_example.template_method.refactoring.shapes.StarShape;

import javax.swing.*;

import static ru.ssau.patterns_example.template_method.refactoring.UIWindow.ShapeState.*;

public class UIWindow {

    private ShapeState state = CIRCLE;

    public UIWindow() {
        JFrame frame = new JFrame("Moving Circle");
        JPanel panel = new JPanel();
        final InteractivePanel iPanel = new InteractivePanel();
        panel.add(iPanel);
        frame.getContentPane().add(panel);
        final JButton button = new JButton("Старт");
        final JButton closeButton = new JButton("Закрыть");
        final JButton changeButton = new JButton("Мячик");
        changeButton.addActionListener(e -> {
            switch (state) {
                case CIRCLE -> {
                    state = SQUARE;
                    changeButton.setText("Квадратик");
                }
                case SQUARE -> {
                    state = STAR;
                    changeButton.setText("Звезда");
                }
                case STAR -> {
                    state = CIRCLE;
                    changeButton.setText("Мячик");
                }
            }
        });
        closeButton.addActionListener(e -> System.exit(0));
        button.addActionListener(e -> {
            switch (state) {
                case CIRCLE -> new Thread(new CircleShape(iPanel, 10)).start();
                case SQUARE -> new Thread(new SquareShape(iPanel, 20)).start();
                case STAR -> new Thread(new StarShape(iPanel)).start();
            }
        });
        panel.add(changeButton);
        panel.add(button);
        panel.add(closeButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 450);
        frame.setVisible(true);
    }

    enum ShapeState {
        CIRCLE, SQUARE, STAR
    }
}
