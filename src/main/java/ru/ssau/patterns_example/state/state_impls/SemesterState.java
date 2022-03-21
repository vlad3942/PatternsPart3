package ru.ssau.patterns_example.state.state_impls;

import ru.ssau.patterns_example.state.State;

public class SemesterState implements State {

    private static final String FILE_NAME = "semester_state.png";

    private static volatile SemesterState instance;

    private SemesterState() {}

    public static SemesterState getInstance() {
        if (instance == null) {
            instance = new SemesterState();
        }
        return instance;
    }

    @Override
    public String getFileName() {
        return FILE_NAME;
    }
}
