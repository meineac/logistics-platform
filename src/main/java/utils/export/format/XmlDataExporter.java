package utils.export.format;

import models.delivery.Shipment;
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
import java.util.List;

public class XmlDataExporter implements DataExporter {
    @Override
    public void export(List<Shipment> data, OutputStream out) throws IOException {
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Dom builder init error: " + e.getMessage(), e);
        }
        Document doc = builder.newDocument();

        Element rootElement = doc.createElement("shipment");
        doc.appendChild(rootElement);

        for (var item : data) {
            Element transport = doc.createElement("transport");

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(item.getTransport().getName()));
            transport.appendChild(name);

            Element time = doc.createElement("time");
            time.appendChild(doc.createTextNode(String.valueOf(item.calculateDeliveryTime())));
            transport.appendChild(time);

            Element cost = doc.createElement("cost");
            cost.appendChild(doc.createTextNode(String.valueOf(item.calculateTotalDeliveryCost())));
            transport.appendChild(cost);

            rootElement.appendChild(transport);
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
