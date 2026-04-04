package service;

import logistics.management.ShipmentAssembler;
import models.delivery.Shipment;
import models.delivery.PayloadBuilder;
import models.LogisticsData;
import models.transport.Transport;
import utils.loader.DataLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogisticsService {
    private DataLoader dataLoader;
    private final ShipmentAssembler factory;
    private final DataExportService exportService;
    private LogisticsData data;

    public LogisticsService(DataLoader dataLoader, ShipmentAssembler factory, DataExportService exportService) {
        this.dataLoader = dataLoader;
        this.factory = factory;
        this.exportService = exportService;
    }

    public void loadSystemData(String filePath) {
        this.data = dataLoader.load(filePath);
    }

    public LogisticsData getAvailableData() {
        if (data == null) throw new IllegalStateException("Data not loaded yet.");
        return data;
    }

    public Shipment arrangeShipment(PayloadBuilder payloadBuilder, String transportName, double distance) {
        Transport transport = getAvailableData().transports().stream()
                .filter(t -> t.getName().equalsIgnoreCase(transportName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Transport not available: " + transportName));

        var finalPayload = payloadBuilder.build();

        return factory.assembleShipment(finalPayload, transport, distance);
    }

    public List<Shipment> calculateAllShipmentOptions(PayloadBuilder payloadBuilder, double distance) {
        var finalPayload = payloadBuilder.build();
        List<Shipment> options = new ArrayList<>();

        for (Transport transport : getAvailableData().transports()) {
            options.add(factory.assembleShipment(finalPayload, transport, distance));
        }
        return options;
    }

    public void setDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public void exportShipments(List<Shipment> sortedShipments, String fileName,
                                DataExportService.Format format, boolean compress,
                                String password) {
        try {
            exportService.exportData(sortedShipments, fileName, format, compress, password);
        } catch (IOException e) {
            throw new RuntimeException("Exporting data error.", e);
        }
    }
}