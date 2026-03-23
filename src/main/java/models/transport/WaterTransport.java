package models.transport;

public class WaterTransport extends Transport {
    private final TransportType type;

    public WaterTransport(String name, double overheads, double speed) {
        super(name, overheads, speed);
        this.type = TransportType.WATER;
    }

    @Override
    public String getType() {
        return type.getName();
    }
}
