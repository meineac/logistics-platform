package logistics.management.filtering;

import models.delivery.Shipment;

import java.util.List;
import java.util.stream.Collectors;

public class MaxTimeFilter extends ShipmentFilter {
    private final double maxTime;

    public MaxTimeFilter(double maxTime) {
        this.maxTime = maxTime;
    }

    @Override
    protected List<Shipment> applyFilter(List<Shipment> shipments) {
        return shipments.stream()
                .filter(s -> s.calculateDeliveryTime() <= maxTime)
                .collect(Collectors.toList());
    }
}
