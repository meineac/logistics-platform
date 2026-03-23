package logistics.transportation;

import models.transport.Transport;
import models.transport.TransportType;
import models.transport.WaterTransport;

public class WaterProvider extends  TransportProvider {
    @Override
    boolean canHandle(String name) {
        return TransportType.WATER.matches(name);
    }

    @Override
    Transport createTransport(String name, double overheads, double speed) {
        return new WaterTransport(name, overheads, speed);
    }
}
