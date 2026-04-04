package utils.export.decorators;

import models.LogisticsData;
import utils.export.format.DataExporter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressingDataExporter extends DataExporterDecorator {
    private final String innerFileName;

    public CompressingDataExporter(DataExporter exporter, String innerFileName) {
        super(exporter);
        this.innerFileName = innerFileName;
    }

    @Override
    public void export(LogisticsData data, OutputStream out) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(out);
        zos.putNextEntry(new ZipEntry(innerFileName));

        super.export(data, zos);

        zos.closeEntry();
        zos.finish();
        zos.flush();
    }
}