package ru.ssau.patterns_example.state.state_impls;

import ru.ssau.patterns_example.state.State;

public class InitialState implements State {

    private static final String FILE_NAME = "initial_state.png";

    @Override
    public String getFileName() {
        return FILE_NAME;
    }
}
