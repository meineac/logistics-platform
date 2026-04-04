package logistics.management.filtering;

import models.delivery.Shipment;
import java.util.List;
import java.util.stream.Collectors;

public class MaxCostFilter extends ShipmentFilter {
    private final double maxCost;

    public MaxCostFilter(double maxCost) {
        this.maxCost = maxCost;
    }

    @Override
    protected List<Shipment> applyFilter(List<Shipment> shipments) {
        return shipments.stream()
                .filter(s -> s.calculateTotalDeliveryCost() <= maxCost)
                .collect(Collectors.toList());
    }
}

