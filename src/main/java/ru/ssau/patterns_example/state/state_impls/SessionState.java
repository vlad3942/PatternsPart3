package ru.ssau.patterns_example.state.state_impls;

import ru.ssau.patterns_example.state.State;

public class SessionState implements State {

    private static final String FILE_NAME = "session_state.png";

    private static volatile SessionState instance;

    private SessionState() {}

    public static SessionState getInstance() {
        if (instance == null) {
            instance = new SessionState();
        }
        return instance;
    }

    @Override
    public String getFileName() {
        return FILE_NAME;
    }
}
