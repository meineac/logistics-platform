package utils.export.format;

import models.delivery.Shipment;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface DataExporter {
    void export(List<Shipment> data, OutputStream out) throws IOException;
}
