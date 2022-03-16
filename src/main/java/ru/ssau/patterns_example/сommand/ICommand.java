package ru.ssau.patterns_example.сommand;

import java.io.OutputStream;

public interface ICommand {
    void execute(final OutputStream os);
}
