package models.transport;

public enum TransportType {

    AIR("air"),
    LAND("land"),
    WATER("water");

    private final String name;

    TransportType(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
