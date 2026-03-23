package utils.parser;

import Infrastructure.TransportProvider;
import models.LogisticsData;
import models.cargo.Cargo;
import models.transport.Transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CsvParser {
    private final List<TransportProvider> transportProviders;

    public CsvParser(List<TransportProvider> transportProviders) {
        this.transportProviders = transportProviders;
    }

    public LogisticsData parseData(List<List<String>> rows) {
        List<Cargo> cargos = new ArrayList<>();
        List<Transport> transports = new ArrayList<>();

        for (List<String> row : rows) {
            if (row == null || row.isEmpty() || row.getFirst() == null) continue;

            String recordType = row.getFirst().toLowerCase();

            if (recordType.equals("cargo")) {
                cargos.add(parseCargo(row));
            } else if (recordType.equals("transport")) {
                transports.add(parseTransport(row));
            }
        }
        return new LogisticsData(cargos, transports);
    }

    private Cargo parseCargo(List<String> row) {
        return new Cargo.Builder()
                .setName(row.get(1))
                .setWeight(Double.parseDouble(row.get(2)))
                .setCost(Double.parseDouble(row.get(3)))
                .build();
    }

    private Transport parseTransport(List<String> row) {
        String name = row.get(1);
        double overheads = Double.parseDouble(row.get(4));
        double speed = Double.parseDouble(row.get(5));

        return transportProviders.stream()
                .map(p -> p.getTransport(name, overheads, speed))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No transport provider found for: " + name));
    }
}