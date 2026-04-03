package service;

import logistics.management.ShipmentAssembler;
import models.delivery.Shipment;
import models.delivery.PayloadBuilder;
import models.LogisticsData;
import models.transport.Transport;
import utils.loader.DataLoader;

public class LogisticsService {
    private final DataLoader dataLoader;
    private final ShipmentAssembler factory;
    private LogisticsData data;

    public LogisticsService(DataLoader dataLoader, ShipmentAssembler factory) {
        this.dataLoader = dataLoader;
        this.factory = factory;
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
}