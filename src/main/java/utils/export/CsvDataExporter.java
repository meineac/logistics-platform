package utils.export;

import models.LogisticsData;
import models.cargo.CargoItem;
import models.transport.Transport;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CsvDataExporter implements DataExporter {
    @Override
    public void export(LogisticsData data, OutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        writer.write("Тип записи;Наименование;Масса_ед_кг;Стоимость_перевозки_за_кг;Расход_на_км;Скорость_км_ч\n");

        for (CargoItem item : data.cargoItems()) {
            writer.write(String.format("cargo;%s;%.1f;%d;;\n",
                    item.getName(), item.getWeight(), (int)item.getCost()));
        }

        for (Transport transport : data.transports()) {
            writer.write(String.format("transport;%s;; ;%.1f;%d\n",
                    transport.getName(), transport.getOverheads(), (int)transport.getSpeed()));
        }
        writer.flush();
    }
}
