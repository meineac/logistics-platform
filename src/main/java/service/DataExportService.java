package service;

import models.delivery.Shipment;
import utils.KeyManager;
import utils.export.format.CsvDataExporter;
import utils.export.format.DataExporter;
import utils.export.format.JsonDataExporter;
import utils.export.format.XmlDataExporter;
import utils.export.decorators.CompressingDataExporter;
import utils.export.decorators.EncryptingDataExporter;

import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DataExportService {

    public enum Format { JSON, CSV, XML }

    public void exportData(List<Shipment> data, String filePath, Format format,
                           boolean compress, String password) throws IOException {

        DataExporter exporter = switch (format) {
            case JSON -> new JsonDataExporter();
            case CSV  -> new CsvDataExporter();
            case XML  -> new XmlDataExporter();
        };

        String innerFileName = "export." + format.name().toLowerCase();

        if (compress) {
            exporter = new CompressingDataExporter(exporter, innerFileName);
            filePath += ".zip";
        }

        if (password != null && !password.isBlank()) {
            SecretKey key = KeyManager.deriveKeyFromPassword(password);
            exporter = new EncryptingDataExporter(exporter, key);
            filePath += ".enc";
        }

        try (OutputStream fos = new FileOutputStream(filePath)) {
            exporter.export(data, fos);
//            fos.flush();
        }

        System.out.println("Export completed successfully to: " + filePath);
    }
}