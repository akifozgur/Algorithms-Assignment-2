import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLParser {

    public static Map<String, Malware> parse(String filename) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Map<String, Malware> malwareDict = new HashMap<String, Malware>();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("row");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    String title = element.getElementsByTagName("title").item(0).getTextContent();
                    String hash = element.getElementsByTagName("hash").item(0).getTextContent();
                    int level = Integer.parseInt(element.getElementsByTagName("level").item(0).getTextContent());

                    malwareDict.put(hash,new Malware(title,level,hash));
                }
            }

        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return malwareDict;
    }
}
