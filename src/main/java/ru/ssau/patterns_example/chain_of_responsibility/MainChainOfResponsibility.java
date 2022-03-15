package ru.ssau.patterns_example.chain_of_responsibility;

import ru.ssau.patterns_example.sub_classes.Auto;
import ru.ssau.patterns_example.sub_classes.DuplicateModelNameException;
import ru.ssau.patterns_example.sub_classes.Transport;

import java.io.*;

public class MainChainOfResponsibility {
    public static void main(String[] args) {
        final Responsible resp1 = new ResponsibleTransportLineFileWriter();
        final Responsible resp2 = new ResponsibleTransportColumnFileWriter();
        resp1.setNext(resp2);
        final Transport tr = new Auto("BMW", 0);
        try {
            tr.addModel("X5", 6.04);
            tr.addModel("Series 5", 4.25);
            tr.addModel("X3", 4.52);
            resp1.writeTransport(tr);
            System.out.println("<==========before adding==========>");
            readFile();
            tr.addModel("X1", 2.1);
            resp1.writeTransport(tr);
            System.out.println("<==========after adding==========>");
            readFile();
        } catch (DuplicateModelNameException e) {
            e.printStackTrace();
        }
    }

    private static void readFile() {
        File file = new File(Responsible.FILE_NAME);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String str = br.readLine();
            while (str != null) {
                System.out.println(str);
                str = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
