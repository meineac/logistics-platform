package models.cargo;

import java.util.List;

public class MixedCargoBatch implements Shippable {
    private final List<Shippable> batches;

    public MixedCargoBatch(List<Shippable> batches) {
        this.batches = batches;
    }

    @Override
    public double getTotalWeight() {
        return batches.stream().mapToDouble(Shippable::getTotalWeight).sum();
    }

    @Override
    public double getTotalCost() {
        return batches.stream().mapToDouble(Shippable::getTotalCost).sum();
    }
}
