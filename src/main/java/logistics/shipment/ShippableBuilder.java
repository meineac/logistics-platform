package logistics.shipment;

import models.cargo.CargoItem;
import models.cargo.MixedCargoBatch;
import models.cargo.Shippable;
import models.cargo.SingleCargoBatch;

import java.util.ArrayList;
import java.util.List;

public class ShippableBuilder {
    private final List<Shippable> items = new ArrayList<>();


    public ShippableBuilder addCargo(CargoItem item, int quantity) {
        items.add(new SingleCargoBatch(item, quantity));
        return this;
    }

    public ShippableBuilder addMixedBatch(Shippable mixedBox) {
        items.add(mixedBox);
        return this;
    }

    public Shippable build() {
        if (items.size() == 1) {
            return items.getFirst();
        }
        return new MixedCargoBatch(items);
    }
}