package models.delivery;

import models.cargo.CargoItem;
import models.cargo.MixedCargoBatch;
import models.cargo.Shippable;
import models.cargo.SingleCargoBatch;

import java.util.ArrayList;
import java.util.List;

public class PayloadBuilder {
    private final List<Shippable> items = new ArrayList<>();


    public PayloadBuilder addCargo(CargoItem item, int quantity) {
        items.add(new SingleCargoBatch(item, quantity));
        return this;
    }

    public PayloadBuilder addMixedBatch(Shippable mixedBox) {
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