package ru.ssau.patterns_example.chain_of_responsibility;

import ru.ssau.patterns_example.sub_classes.Transport;

public interface Responsible {

    String FILE_NAME = "CoR.txt";

    void writeTransport(Transport transport);
    void setNext(Responsible resp);
}
