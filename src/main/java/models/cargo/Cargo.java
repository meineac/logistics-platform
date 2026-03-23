package models.cargo;

public class Cargo {
    private final String name;
    private final double weight;
    private final double cost;

    public Cargo(Builder builder) {
        this.name = builder.name;
        this.weight = builder.weight;
        this.cost = builder.cost;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getCost() {
        return cost;
    }

    public static class Builder {
        private String name;
        private double weight;
        private double cost;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder setCost(double cost) {
            this.cost = cost;
            return this;
        }

        public Cargo build() {
            return new Cargo(this);
        }
    }
}
