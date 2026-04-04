package utils.export;

import models.LogisticsData;
import utils.KeyManager;
import utils.export.format.CsvDataExporter;
import utils.export.format.DataExporter;
import utils.export.format.JsonDataExporter;
import utils.export.format.XmlDataExporter;
import utils.export.decorators.CompressionExporterDecorator;
import utils.export.decorators.EncryptionExporterDecorator;

import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LogisticsDataExporter {

    public enum Format { JSON, CSV, XML }

    public void exportData(LogisticsData data, String filePath, Format format,
                           boolean compress, boolean encrypt,
                           String password) throws IOException {

        DataExporter exporter = switch (format) {
            case JSON -> new JsonDataExporter();
            case CSV  -> new CsvDataExporter();
            case XML  -> new XmlDataExporter();
        };

        String innerFileName = "export." + format.name().toLowerCase();

        if (compress) {
            exporter = new CompressionExporterDecorator(exporter, innerFileName);
            filePath += ".zip";
        }

        if (password != null && !password.isBlank()) {
            SecretKey key = KeyManager.deriveKeyFromPassword(password);
            exporter = new EncryptionExporterDecorator(exporter, key);
            filePath += ".enc";
        }

        try (OutputStream fos = new FileOutputStream(filePath)) {
            exporter.export(data, fos);
        }

        System.out.println("Export completed successfully to: " + filePath);
    }
}