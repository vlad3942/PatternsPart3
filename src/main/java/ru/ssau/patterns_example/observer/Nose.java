package ru.ssau.patterns_example.observer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Nose extends MouseAdapter {

    public static final String FILE_NAME = "Nose.png";

    private NoseState state;

    private final MainWindow window;

    public Nose(final MainWindow window) {
        state = NoseState.NORMAL;
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (
                (x < 335) && (x > 230)
                && (y < 400) && (y > 330)
        ) {
            if (state == NoseState.NORMAL) {
                state = NoseState.RED;
            } else {
                state = NoseState.NORMAL;
            }
            window.redraw();
        }
    }

    public boolean isNormal() {
        return state == NoseState.NORMAL;
    }

    private enum NoseState {
        NORMAL, RED
    }
}
