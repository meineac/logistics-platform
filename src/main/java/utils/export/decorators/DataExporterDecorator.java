package utils.export.decorators;

import models.LogisticsData;
import utils.export.format.DataExporter;

import java.io.IOException;
import java.io.OutputStream;

public class DataExporterDecorator implements DataExporter {
    protected final DataExporter exporter;

    public DataExporterDecorator(DataExporter exporter) {
        this.exporter = exporter;
    }

    @Override
    public void export(LogisticsData data, OutputStream out) throws IOException {
        exporter.export(data, out);
    }
}
