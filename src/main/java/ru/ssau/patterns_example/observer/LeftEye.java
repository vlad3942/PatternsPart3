package ru.ssau.patterns_example.observer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LeftEye extends MouseAdapter {
    public static final String FILE_NAME = "LeftEye.png";

    private EyeState state;

    private final MainWindow window;

    public LeftEye(final MainWindow window) {
        state = EyeState.OPENED;
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (
                (x < 260) && (x > 170)
                        && (y < 310) && (y > 260)
        ) {
            if (state == EyeState.CLOSED) {
                state = EyeState.OPENED;
            } else {
                state = EyeState.CLOSED;
            }
            window.redraw();
        }
    }

    public boolean isOpened() {
        return state == EyeState.OPENED;
    }
}
