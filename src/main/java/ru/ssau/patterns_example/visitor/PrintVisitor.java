package ru.ssau.patterns_example.visitor;

import ru.ssau.patterns_example.sub_classes.Auto;
import ru.ssau.patterns_example.sub_classes.Moto;
import ru.ssau.patterns_example.sub_classes.TestClass;

public class PrintVisitor implements Visitor{
    @Override
    public void visit(Auto auto) {
        TestClass.writeInLine(auto, "auto-in-line.txt");
    }

    @Override
    public void visit(Moto moto) {
        TestClass.writeInColumn(moto, "moto-in-column.txt");
    }
}
