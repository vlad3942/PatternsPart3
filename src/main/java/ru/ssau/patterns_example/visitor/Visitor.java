package ru.ssau.patterns_example.visitor;

import ru.ssau.patterns_example.sub_classes.Auto;
import ru.ssau.patterns_example.sub_classes.Moto;

public interface Visitor {

    void visit(Auto auto);
    void visit(Moto moto);
}
