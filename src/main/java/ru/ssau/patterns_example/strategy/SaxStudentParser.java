package ru.ssau.patterns_example.strategy;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class SaxStudentParser implements StudentXMLDocumentParser {

    @Override
    public void parseDoc(final String input, final String output) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            PrintAllHandlerSax handler = new PrintAllHandlerSax(new FileOutputStream(output));
            saxParser.parse(CLASS_LOADER.getResourceAsStream(input), handler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    static class PrintAllHandlerSax extends DefaultHandler {

        private double sum;
        private int counter;
        private final StringBuilder doc = new StringBuilder();
        private final OutputStream os;

        private final StringBuilder currentValue = new StringBuilder();

        PrintAllHandlerSax(final OutputStream os) {
            this.os = os;
        }

        @Override
        public void startDocument() {
            System.out.println("Start Document");
            doc.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        }

        @Override
        public void endDocument() {
            System.out.println("End Document");
            try {
                os.write(doc.toString().getBytes(Charset.defaultCharset()));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void startElement(
                String uri,
                String localName,
                String qName,
                Attributes attributes) {

            // reset the tag value
            currentValue.setLength(0);

            System.out.printf("Start Element : %s%n", qName);

            if (attributes.getLength() == 0) {
                doc.append("<").append(qName).append(">");
            }

            if (qName.equalsIgnoreCase("student")) {
                sum = 0;
                counter = 0;
                doc.append("<").append(qName)
                        .append(" lastname=\"")
                        .append(attributes.getValue("lastname"))
                        .append("\" ")
                        .append(">");
            }

            if (qName.equalsIgnoreCase("subject")) {
                final String title = attributes.getValue("title");
                final int mark = Integer.parseInt(attributes.getValue("mark"));
                sum += mark;
                counter++;
                System.out.println(qName + ":\t" + title + "\t" + mark);
                doc.append("<").append(qName)
                        .append(" title=\"")
                            .append(title)
                        .append("\"")
                        .append(" mark=\"")
                            .append(mark)
                        .append("\" ")
                    .append("/>");
            }

        }

        @Override
        public void endElement(String uri,
                               String localName,
                               String qName) {

            System.out.printf("End Element : %s%n", qName);

            if (qName.equalsIgnoreCase("average")) {
                final String val = currentValue.toString();
                long avg = Long.parseLong(val);
                long avgRes = Math.round(sum / counter);
                if (avgRes != avg) {
                    System.out.println("file average: " + avg);
                    System.out.println("calculated average: " + avgRes);
                    avg = avgRes;
                }
                doc.append(avg);
            }

            if (currentValue.length() > 0)
                doc.append("</").append(qName).append(">");
        }

        // http://www.saxproject.org/apidoc/org/xml/sax/ContentHandler.html#characters%28char%5B%5D,%20int,%20int%29
        // SAX parsers may return all contiguous character data in a single chunk,
        // or they may split it into several chunks
        @Override
        public void characters(char ch[], int start, int length) {

            // The characters() method can be called multiple times for a single text node.
            // Some values may missing if assign to a new string

            // avoid doing this
            // value = new String(ch, start, length);

            // better append it, works for single or multiple calls
            currentValue.append(ch, start, length);
        }

    }
}
