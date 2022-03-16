package ru.ssau.patterns_example.сommand;

import ru.ssau.patterns_example.sub_classes.Auto;
import ru.ssau.patterns_example.sub_classes.NoSuchModelNameException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class PrintInColumnCommand implements ICommand{

    private final Auto auto;

    public PrintInColumnCommand(final Auto auto) {
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
            sb.append("\n{");
            for (String name : names) {
                sb.append("\n");
                sb.append(name);
                sb.append(" = ");
                sb.append(auto.getCostOfCurrentModel(name));
            }
            sb.append("\n}");
            osw.write(sb.toString());
            osw.flush();
        } catch (IOException | NoSuchModelNameException e) {
            e.printStackTrace();
        }
    }
}
