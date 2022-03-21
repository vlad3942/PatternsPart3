package ru.ssau.patterns_example.state;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class StudentContextHolder {

    private State state;

    public StudentContextHolder(State initialState) {
        this.state = initialState;
    }

    public BufferedImage getStateDependentImage() throws IOException {
        return state.getStateImage();
    }

    public void changeState(State state) {
        this.state = state;
    }
}
