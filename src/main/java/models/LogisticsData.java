package models;

import models.cargo.CargoItem;
import models.transport.Transport;
import java.util.List;

public record LogisticsData(List<CargoItem> cargoItems, List<Transport> transports) { }