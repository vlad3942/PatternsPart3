package ru.ssau.patterns_example.observer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RightEye extends MouseAdapter {
    public static final String FILE_NAME = "RightEye.png";

    private EyeState state;

    private final MainWindow window;

    public RightEye(final MainWindow window) {
        state = EyeState.OPENED;
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (
                (x < 400) && (x > 320)
                        && (y < 320) && (y > 270)
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
