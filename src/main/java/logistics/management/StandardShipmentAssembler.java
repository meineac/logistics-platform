package logistics.management;

import models.delivery.Shipment;
import models.cargo.Shippable;
import models.transport.Transport;

public class StandardShipmentAssembler implements ShipmentAssembler {
    @Override
    public Shipment assembleShipment(Shippable payload, Transport transport, double distance) {
        return new Shipment(payload, transport, distance);
    }
}
