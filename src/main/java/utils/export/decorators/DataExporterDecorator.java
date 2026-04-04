package utils.export.decorators;

import models.delivery.Shipment;
import utils.export.format.DataExporter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DataExporterDecorator implements DataExporter {
    protected final DataExporter exporter;

    public DataExporterDecorator(DataExporter exporter) {
        this.exporter = exporter;
    }

    @Override
    public void export(List<Shipment> data, OutputStream out) throws IOException {
        exporter.export(data, out);
    }
}
