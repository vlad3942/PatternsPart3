package ru.ssau.patterns_example.сommand;

import ru.ssau.patterns_example.sub_classes.Auto;
import ru.ssau.patterns_example.sub_classes.NoSuchModelNameException;

import java.io.*;

public class PrintInLineCommand implements ICommand{

    private final Auto auto;

    public PrintInLineCommand(Auto auto) {
        this.auto = auto;
    }

    @Override
    public void execute(final OutputStream os) {
        try (OutputStreamWriter osw = new OutputStreamWriter(os)) {
            StringBuilder sb = new StringBuilder();
            sb.append(auto.getMark())
                    .append(" models amount ")
                    .append(auto.getModelsLength())
                    .append(": ");
            final String[] names = auto.getModelsNames();
            for (String name : names) {
                sb.append("\t");
                sb.append(name);
                sb.append(" = ");
                sb.append(auto.getCostOfCurrentModel(name));
            }
            osw.write(sb.toString());
            osw.flush();
        } catch (IOException | NoSuchModelNameException e) {
            e.printStackTrace();
        }
    }
}
