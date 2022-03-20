package ru.ssau.patterns_example.observer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouth extends MouseAdapter {
    public static final String FILE_NAME = "Mouth.png";

    private MouthState state;

    private final MainWindow window;

    public Mouth(final MainWindow window) {
        state = MouthState.NORMAL;
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (
                (x < 350) && (x > 210)
                        && (y < 490) && (y > 430)
        ) {
            if (state == MouthState.NORMAL) {
                state = MouthState.SMILE;
            } else {
                state = MouthState.NORMAL;
            }
            window.redraw();
        }
    }

    public boolean isNormal() {
        return state == MouthState.NORMAL;
    }

    private enum MouthState {
        NORMAL, SMILE
    }
}
