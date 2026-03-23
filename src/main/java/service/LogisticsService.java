package service;

import logistics.management.ShipmentAssembler;
import models.delivery.Shipment;
import models.delivery.PayloadBuilder;
import models.LogisticsData;
import models.transport.Transport;
import utils.reader.Reader;

public class LogisticsService {
    private final Reader dataReader;
    private final ShipmentAssembler factory;
    private LogisticsData data;

    public LogisticsService(Reader dataReader, ShipmentAssembler factory) {
        this.dataReader = dataReader;
        this.factory = factory;
    }

    public void loadSystemData(String filePath) {
        this.data = dataReader.read(filePath);
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