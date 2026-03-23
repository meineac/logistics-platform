package models.transport;

public abstract class Transport {
    private final String name;
    private final double overheads;
    private final double speed;

    public Transport(String name, double overheads, double speed) {
        this.name = name;
        this.overheads = overheads;
        this.speed = speed;
    }

    public abstract String getType();

    public String getName() {
        return name;
    }

    public double getOverheads() {
        return overheads;
    }

    public double getSpeed() {
        return speed;
    }
}
