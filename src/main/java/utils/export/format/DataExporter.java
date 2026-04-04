package utils.export.format;

import models.LogisticsData;

import java.io.IOException;
import java.io.OutputStream;

public interface DataExporter {
    void export(LogisticsData data, OutputStream out) throws IOException;
}
