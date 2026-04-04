package utils.export.format;

import models.delivery.Shipment;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvDataExporter implements DataExporter {
    @Override
    public void export(List<Shipment> data, OutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        writer.write("Транспорт;Время;Стоимость;\n");

        for (var item : data) {
            writer.write(String.format("%s;%.1f;%.2f\n",
                    item.getTransport().getName(), item.calculateDeliveryTime(), item.calculateTotalDeliveryCost()));
        }

        writer.flush();
    }
}
