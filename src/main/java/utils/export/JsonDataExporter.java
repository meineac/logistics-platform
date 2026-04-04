package utils.export;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.LogisticsData;

import java.io.IOException;
import java.io.OutputStream;

public class JsonDataExporter implements  DataExporter {
    @Override
    public void export(LogisticsData data, OutputStream out) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(Feature.AUTO_CLOSE_TARGET, false);
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, data);
        out.flush();
    }
}
