package models.delivery;

import models.cargo.Shippable;
import models.transport.Transport;

public class Shipment {
    private final Shippable payload;
    private final Transport transport;
    private final double distance;

    public Shipment(Shippable payload, Transport transport, double distance) {
        this.payload = payload;
        this.transport = transport;
        this.distance = distance;
    }

    public double calculateTotalDeliveryCost() {
        double cargoCost = payload.getTotalCost();
        double transportCost = transport.calculateTransportCost(distance);
        return cargoCost + transportCost;
    }

    public double calculateDeliveryTime() {
        return distance / transport.getSpeed();
    }
}