package models.transport;

public class LandTransport extends Transport {
    private final TransportType type;

    public LandTransport(String name, double overheads, double speed) {
        super(name, overheads, speed);
        this.type = TransportType.LAND;
    }

    @Override
    public String getType() {
        return type.getName();
    }
}
