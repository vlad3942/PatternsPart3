package ru.ssau.patterns_example.sub_classes;

public final class AutoUtils {
    public static Auto initAuto() {
        final Auto tr = new Auto("BMW", 0);
        try {
            tr.addModel("X5", 6.04);
            tr.addModel("Series 5", 4.25);
            tr.addModel("X3", 4.52);
        } catch (DuplicateModelNameException e) {
            e.printStackTrace();
        }
        return tr;
    }
}
