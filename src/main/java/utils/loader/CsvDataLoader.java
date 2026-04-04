package utils.loader;

import models.LogisticsData;
import models.cargo.CargoItem;
import models.transport.Transport;
import utils.helper.EntityAssembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvDataLoader extends DataLoaderTemplate {

    public CsvDataLoader(EntityAssembler assembler) {
        super(assembler);
    }

    @Override
    public LogisticsData processData(String filePath) throws IOException {
        var rows = read(filePath);
        List<CargoItem> cargoItems = new ArrayList<>();
        List<Transport> transports = new ArrayList<>();
        for (List<String> row : rows) {
            if (row == null || row.isEmpty() || row.getFirst() == null) continue;
            String recordType = row.getFirst().toLowerCase();
            if (recordType.equals("cargo")) {
                cargoItems.add(assembler.assembleCargo(
                        row.get(1),
                        Double.parseDouble(row.get(2)),
                        Double.parseDouble(row.get(3))
                ));
            } else if (recordType.equals("transport")) {
                transports.add(assembler.assembleTransport(
                        row.get(1),
                        Double.parseDouble(row.get(4)),
                        Double.parseDouble(row.get(5))
                ));
            }
        }
        return new LogisticsData(cargoItems, transports);
    }

    private List<List<String>> read(String filePath) throws IOException {
        try(var lines = Files.lines(Path.of(filePath))) {
            return lines
                    .skip(1)
                    .map(line -> line.split(";", -1))
                    .map(columns -> Arrays.stream(columns)
                            .map(String::strip)
                            .map(s -> s.isEmpty() ? null : s)
                            .toList())
                    .toList();
        }
    }
}
