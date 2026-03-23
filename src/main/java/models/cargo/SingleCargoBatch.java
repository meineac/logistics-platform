package models.cargo;

public class SingleCargoBatch implements Shippable {
    private final CargoItem item;
    private final int quantity;

    public SingleCargoBatch(CargoItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    @Override
    public double getTotalWeight() {
        return item.getWeight() * quantity;
    }

    @Override
    public double getTotalCost() {
        return item.calculateItemCost() * quantity;
    }
}
