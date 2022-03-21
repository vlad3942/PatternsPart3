package ru.ssau.patterns_example.strategy;

public interface StudentXMLDocumentParser {

    ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

    void parseDoc(final String input, final String output);
}
