package utils.export.format;

import models.LogisticsData;
import models.cargo.CargoItem;
import models.transport.Transport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;

public class XmlDataExporter implements DataExporter {
    @Override
    public void export(LogisticsData data, OutputStream out) throws IOException {
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Dom builder init error: " + e.getMessage(), e);
        }
        Document doc = builder.newDocument();

        Element rootElement = doc.createElement("logisticsData");
        doc.appendChild(rootElement);

        Element cargoItems = doc.createElement("cargoItems");
        rootElement.appendChild(cargoItems);

        for (CargoItem item : data.cargoItems()) {
            Element cargo = doc.createElement("cargo");

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(item.getName()));
            cargo.appendChild(name);

            Element weight = doc.createElement("weight");
            weight.appendChild(doc.createTextNode(String.valueOf(item.getWeight())));
            cargo.appendChild(weight);

            Element cost = doc.createElement("cost");
            cost.appendChild(doc.createTextNode(String.valueOf(item.getCost())));
            cargo.appendChild(cost);

            cargoItems.appendChild(cargo);
        }

        Element transports = doc.createElement("transports");
        rootElement.appendChild(transports);

        for (Transport item : data.transports()) {
            Element transport = doc.createElement("transport");

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(item.getName()));
            transport.appendChild(name);

            Element overheads = doc.createElement("overheads");
            overheads.appendChild(doc.createTextNode(String.valueOf(item.getOverheads())));
            transport.appendChild(overheads);

            Element speed = doc.createElement("speed");
            speed.appendChild(doc.createTextNode(String.valueOf(item.getSpeed())));
            transport.appendChild(speed);

            transports.appendChild(transport);
        }

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(out);
            transformer.transform(source, result);
            out.flush();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException("Error creating transformer: " + e.getMessage(), e);
        } catch (TransformerException e) {
            throw new RuntimeException("Error writing result: " + e.getMessage(), e);
        }
    }
}
