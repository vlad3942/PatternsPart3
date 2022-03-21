package ru.ssau.patterns_example.strategy;

public class StrategyMainTest {

    public static void main(String[] args) {
        if (args != null && args.length >= 2) {
            if (args[0] == null || args[0].isEmpty()) {
                throw new IllegalArgumentException("Input file name is null or empty.");
            }
            System.out.println("Input file name: " + args[0]);
            if (args[1] == null || args[1].isEmpty()) {
                throw new IllegalArgumentException("Output file name is null or empty.");
            }
            System.out.println("Output file name: " + args[1]);
            StudentXMLDocumentParser parser = new DomStudentParser();
            if (args[1].contains("sax")) {
                parser = new SaxStudentParser();
            }
            parser.parseDoc(args[0], args[1]);
        } else {
            throw new IllegalArgumentException("Need 2 parameters: input file name and output file name.");
        }
    }
}
