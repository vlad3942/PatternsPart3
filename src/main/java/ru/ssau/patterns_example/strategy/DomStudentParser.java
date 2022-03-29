package ru.ssau.patterns_example.strategy;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class DomStudentParser implements StudentXMLDocumentParser{

    private final DocumentBuilderFactory DOC_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    @Override
    public void parseDoc(final String input, final String output) {
        try {
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            DOC_BUILDER_FACTORY.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = DOC_BUILDER_FACTORY.newDocumentBuilder();
            Document doc = db.parse(Objects.requireNonNull(CLASS_LOADER.getResource(input)).getFile());

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");
            // get <staff>
            NodeList list = doc.getElementsByTagName("student");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element student = (Element) node;
                    String lastname = student.getAttribute("lastname");
                    System.out.println("Студент: " + lastname);

                    double avg = Integer.parseInt(student.getElementsByTagName("average").item(0).getTextContent());
                    System.out.println("Средняя оценка: " + avg);

                    NodeList subjects = student.getElementsByTagName("subject");

                    double sum = 0;
                    for (int i = 0; i < subjects.getLength(); i++) {
                        node = subjects.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element subj = (Element) node;
                            final String subjName = subj.getAttribute("title");
                            final int mark = Integer.parseInt(subj.getAttribute("mark"));
                            System.out.println("Предмет \"" + subjName + "\", оценка: " + mark);
                            sum += mark;
                        }
                    }
                    double avgReal = sum / subjects.getLength();
                    System.out.println("Посчитанная стредняя: " + avgReal);

                    if (avg != avgReal) {
                        student.getElementsByTagName("average").item(0).setTextContent(String.valueOf(avgReal));
                    }
                }
            }
            try {
                OutputStream os = new FileOutputStream(output);
                writeXml(doc, os);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    // write doc to output stream
    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
}
