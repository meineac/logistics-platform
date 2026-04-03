package utils.loader;

import models.LogisticsData;
import models.cargo.CargoItem;
import models.transport.Transport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.helper.EntityAssembler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlDataLoader extends TemplateDataLoader {
    public XmlDataLoader(EntityAssembler assembler) {
        super(assembler);
    }

    @Override
    public LogisticsData processData(String filePath) throws IOException {
        List<CargoItem> cargoItems;
        List<Transport> transports;

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            doc.getDocumentElement().normalize();

            NodeList cargoItemList = doc.getElementsByTagName("cargo");

            cargoItems = parseCargos(cargoItemList);

            NodeList transportList = doc.getElementsByTagName("transport");

            transports = parseTransport(transportList);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Fatal Error: The XML parser could not be configured properly.", e);
        } catch (SAXException e) {
            throw new RuntimeException("Parse Error: The XML file is malformed or invalid.", e);
        }

        return new LogisticsData(cargoItems, transports);
    }

    private Element getElementByName(Node parent, String tagName) {
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            Node node =  children.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(tagName)) {
                return (Element) node;
            }
        }
        return null;
    }

    private double parseDouble(String rawValue, String fieldName) {
        if (rawValue == null || rawValue.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Invalid XML format: " + fieldName + " cannot be empty."
            );
        }

        String cleanValue = rawValue.trim();
        try {
            return Double.parseDouble(cleanValue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid XML format: expected a numeric value for " + fieldName +
                            " but got '" + cleanValue + "'.", e
            );
        }
    }

    private Element getRequiredElement(Node parent, String tagName, String context) {
        Element element = getElementByName(parent, tagName);
        if (element == null) {
            throw new IllegalArgumentException(
                    "Invalid XML format: " + context + tagName + " not found"
            );
        }
        return element;
    }

    private List<CargoItem> parseCargos(NodeList cargoItemList) {
        List<CargoItem> cargoItems = new ArrayList<>();

            int n = cargoItemList.getLength();
            for (int i = 0; i < n; ++i) {
                Node current = cargoItemList.item(i);

                Element cargoItemName = getRequiredElement(current, "name", "cargo item");
                Element cargoItemWeight = getRequiredElement(current, "weight", "cargo item");
                Element cargoItemCost = getRequiredElement(current, "cost", "cargo item");

                cargoItems.add(assembler.assembleCargo(
                        cargoItemName.getTextContent().strip(),
                        parseDouble(cargoItemWeight.getTextContent().strip(), "weight"),
                        parseDouble(cargoItemCost.getTextContent().strip(), "cost")
                ));
            }
            return cargoItems;
    }

    private List<Transport> parseTransport(NodeList transportsList) {
        List<Transport> transports = new ArrayList<>();

        int n = transportsList.getLength();
        for (int i = 0; i < n; ++i) {
            Node current = transportsList.item(i);

            Element transportName = getRequiredElement(current, "name", "transport");
            Element transportOverheads = getRequiredElement(current, "overheads", "transport");
            Element transportSpeed = getRequiredElement(current, "speed", "transport");

            transports.add(assembler.assembleTransport(
                    transportName.getTextContent().strip(),
                    parseDouble(transportOverheads.getTextContent().strip(), "overheads"),
                    parseDouble(transportSpeed.getTextContent().strip(), "speed")
            ));
        }
        return transports;
    }
}
