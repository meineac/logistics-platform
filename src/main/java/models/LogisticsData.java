package models;

import models.cargo.Cargo;
import models.transport.Transport;
import java.util.List;

public record LogisticsData(List<Cargo> cargos, List<Transport> transports) { }