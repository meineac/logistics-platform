package models.transport;

import java.util.Arrays;

public enum TransportType {
    AIR("Air", "air", "воздух"),
    LAND("Land", "land", "земля"),
    WATER("Water", "water", "вода");

    private final String name;
    private final String[] keywords;

    TransportType(String name, String... keywords) {
        this.name = name;
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public boolean matches(String text) {
        if (text == null) return false;
        String lower = text.toLowerCase();
        return Arrays.stream(keywords).anyMatch(lower::contains);
    }
}