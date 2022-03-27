package ru.ssau.patterns_example.iterator;

import ru.ssau.patterns_example.sub_classes.Auto;
import ru.ssau.patterns_example.sub_classes.TransportUtils;

import java.util.Iterator;

public class IteratorMainTest {
    public static void main(String[] args) {
        Auto auto = TransportUtils.initAuto();
        Iterator iterator = auto.iterator();
        iterator.forEachRemaining(System.out::println);
    }
}
