package ru.ssau.patterns_example.memento;

import ru.ssau.patterns_example.sub_classes.Auto;
import ru.ssau.patterns_example.sub_classes.TransportUtils;
import ru.ssau.patterns_example.sub_classes.DuplicateModelNameException;
import ru.ssau.patterns_example.sub_classes.NoSuchModelNameException;

public class MementoMainTest {
    public static void main(String[] args) throws DuplicateModelNameException, NoSuchModelNameException {
        final Auto auto = TransportUtils.initAuto();
        System.out.println("Create ->");
        System.out.println(auto);
        System.out.println("Saving state...");
        final Auto.AutoMemento memento = auto.createMemento();
        System.out.println("Add changes in to the auto ->");
        auto.setMark("123456");
        auto.addModel("Hello", 123);
        auto.delModel(auto.getModelsNames()[0]);
        System.out.println(auto);
        System.out.println("Rollback changes from memento ->");
        auto.setMemento(memento);
        System.out.println(auto);
    }
}
