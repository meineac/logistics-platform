package logistics.management.filtering;

import models.delivery.Shipment;

import java.util.List;

public abstract class ShipmentFilter {
    private ShipmentFilter nextFilter;

    public ShipmentFilter linkWith(ShipmentFilter nextFilter) {
        this.nextFilter = nextFilter;
        return nextFilter;
    }

    public List<Shipment> filter(List<Shipment> shipments) {
        List<Shipment> filtered = applyFilter(shipments);
        if (nextFilter != null) {
            return nextFilter.filter(filtered);
        }
        return filtered;
    }

    protected abstract List<Shipment> applyFilter(List<Shipment> shipments);
}
