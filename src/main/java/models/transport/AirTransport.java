package models.transport;

public class AirTransport extends Transport {
    private final TransportType type;

    public AirTransport(String name, double overheads, double speed) {
        super(name, overheads, speed);
        this.type = TransportType.AIR;
    }

    @Override
    public String getType() {
        return type.getName();
    }
}
