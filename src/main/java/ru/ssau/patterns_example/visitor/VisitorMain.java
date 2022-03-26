package ru.ssau.patterns_example.visitor;

import ru.ssau.patterns_example.sub_classes.Transport;
import ru.ssau.patterns_example.sub_classes.TransportUtils;

public class VisitorMain {
    public static void main(String[] args) {
        final PrintVisitor printVisitor = new PrintVisitor();
        final Transport trAuto = TransportUtils.initAuto();
        final Transport trMoto = TransportUtils.initMoto();
        trAuto.accept(printVisitor);
        trMoto.accept(printVisitor);
    }
}
