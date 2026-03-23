package logistics.transportation;

import models.transport.AirTransport;
import models.transport.Transport;
import models.transport.TransportType;

public class AirProvider extends TransportProvider {
    @Override
    boolean canHandle(String name) {
        return TransportType.AIR.matches(name);
    }

    @Override
    Transport createTransport(String name, double overheads, double speed) {
        return new AirTransport(name, overheads, speed);
    }
}
