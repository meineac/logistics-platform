package logistics.management.sorting;

import models.delivery.Shipment;
import java.util.Comparator;
import java.util.List;

public class ShipmentSorter {
    private Comparator<Shipment> strategy;

    public void setStrategy(Comparator<Shipment> strategy) {
        this.strategy = strategy;
    }

    public void addStrategy(Comparator<Shipment> additionalStrategy) {
        if (this.strategy == null) {
            setStrategy(additionalStrategy);
        } else {
            this.strategy = this.strategy.thenComparing(additionalStrategy);
        }
    }

    public void sort(List<Shipment> shipments) {
        if (strategy != null) {
            shipments.sort(strategy);
        }
    }
}