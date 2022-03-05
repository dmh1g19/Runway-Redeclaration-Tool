package uk.ac.soton.comp2211.utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;

public class XMLUtil {

    //TODO: pass filename through user input
    String fileName = "airports.xml";
    String directory = "src/main/resources/uk/ac/soton/comp2211/" + fileName;

    DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();

    public void scanDocument() throws Exception {
        try {
            DocumentBuilder build = fac.newDocumentBuilder();
            Document document = build.parse(directory);

            NodeList nodeList = document.getElementsByTagName("runway");

            for(int i=0;i<nodeList.getLength();i++) {
                Node curNode = nodeList.item(i);

                if(curNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) curNode;

                    //TODO: pass to runway/airport class, get airport name?
                    String name = getElementContentStr(element, "name");
                    String TORA = getElementContentStr(element, "TORA");
                    String TODA  = getElementContentStr(element, "TODA");
                    String ASDA = getElementContentStr(element, "ASDA");
                    String LDA = getElementContentStr(element, "LDA");
                    String stripLength = getElementContentStr(element, "stripLength");
                    String stripWidth = getElementContentStr(element, "stripWidth");
                    String clearwayLength = getElementContentStr(element, "clearwayLength");
                    String clearwayWidth = getElementContentStr(element, "clearwayWidth");
                }
            }
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public String getElementContentStr (Element element, String attr) {
        Node node = element.getElementsByTagName(attr).item(0);
        return node.getTextContent();
    }
}
