package models.transport;

public class WaterTransport implements Transport {
    private final String name;
    private final double overheads;
    private final double speed;
    private final TransportType type;

    public WaterTransport(String name, double overheads, double speed) {
        this.name = name;
        this.overheads = overheads;
        this.speed = speed;
        this.type = TransportType.WATER;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getOverheads() {
        return overheads;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public String getType() {
        return type.getName();
    }
}
