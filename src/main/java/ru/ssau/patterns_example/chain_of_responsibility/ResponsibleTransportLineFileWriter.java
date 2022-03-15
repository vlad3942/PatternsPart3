package ru.ssau.patterns_example.chain_of_responsibility;

import ru.ssau.patterns_example.sub_classes.TestClass;
import ru.ssau.patterns_example.sub_classes.Transport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResponsibleTransportLineFileWriter implements Responsible {

    private Responsible nextResponsible;

    @Override
    public void writeTransport(Transport transport) {
        try {
            if (transport.getModelsLength() <= 3) {
                TestClass.writeInLine(transport, FILE_NAME);
            } else {
                throw new IllegalStateException("Unsupported operation exception");
            }
        } catch (IllegalStateException ise) {
            if (nextResponsible == null) {
                throw new IllegalStateException("Next responsible object is null", ise);
            }
            nextResponsible.writeTransport(transport);
        }
    }

    @Override
    public void setNext(Responsible resp) {
        nextResponsible = resp;
    }
}
