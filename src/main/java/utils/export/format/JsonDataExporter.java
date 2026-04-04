package utils.export.format;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.delivery.Shipment;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class JsonDataExporter implements  DataExporter {
    @Override
    public void export(List<Shipment> data, OutputStream out) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(Feature.AUTO_CLOSE_TARGET, false);
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, data);
        out.flush();
    }
}
