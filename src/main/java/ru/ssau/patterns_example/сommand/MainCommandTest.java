package ru.ssau.patterns_example.сommand;

import ru.ssau.patterns_example.sub_classes.Auto;
import ru.ssau.patterns_example.sub_classes.TransportUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainCommandTest {
    public static void main(String[] args) {
        final File inLineFile = new File("inLine.txt");
        final File inColumnFile = new File("inColumn.txt");
        try (
                FileOutputStream lineOut = new FileOutputStream(inLineFile);
                FileInputStream lineIn = new FileInputStream(inLineFile);
                FileOutputStream columnOut = new FileOutputStream(inColumnFile);
                FileInputStream columnIn = new FileInputStream(inColumnFile)
        ) {
            Auto auto = TransportUtils.initAuto();
            ICommand command = new PrintInLineCommand(auto);
            auto.setPrintCommand(command);
            System.out.println("Printing auto in file as line...");
            auto.print(lineOut);
            System.out.println("check - 1:");
            readFile(lineIn);

            System.out.println("<============================================>");

            command = new PrintInColumnCommand(auto);
            auto.setPrintCommand(command);
            System.out.println("Printing auto in file as column...");
            auto.print(columnOut);
            System.out.println("check - 2:");
            readFile(columnIn);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void readFile(final FileInputStream fis) {
        try {
            System.out.println(new String(fis.readAllBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
