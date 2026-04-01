package utils.helper;

import logistics.transportation.TransportProvider;
import models.cargo.CargoItem;
import models.transport.Transport;

import java.util.List;
import java.util.Objects;

public class EntityAssembler {
    private final List<TransportProvider> transportProviders;

    public EntityAssembler(List<TransportProvider> transportProviders) {
        this.transportProviders = transportProviders;
    }

    public CargoItem assembleCargo(String name, double weight, double cost) {
        return new CargoItem.Builder()
                .setName(name)
                .setWeight(weight)
                .setCost(cost)
                .build();
    }

    public Transport assembleTransport(String name, double overheads, double speed) {
        return transportProviders.stream()
                .map(p -> p.getTransport(name, overheads, speed))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No transport provider found for: " + name));
    }
}