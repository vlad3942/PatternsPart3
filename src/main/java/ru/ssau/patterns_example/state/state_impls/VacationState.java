package ru.ssau.patterns_example.state.state_impls;

import ru.ssau.patterns_example.state.State;

public class VacationState implements State {
    private static final String FILE_NAME = "vacation_state.png";

    private static volatile VacationState instance;

    private VacationState() {}

    public static VacationState getInstance() {
        if (instance == null) {
            instance = new VacationState();
        }
        return instance;
    }

    @Override
    public String getFileName() {
        return FILE_NAME;
    }
}
