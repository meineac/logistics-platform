package logistics.management;

import models.delivery.Shipment;
import models.cargo.Shippable;
import models.transport.Transport;

public interface ShipmentAssembler {
    Shipment assembleShipment(Shippable payload, Transport transport, double distance);
}
