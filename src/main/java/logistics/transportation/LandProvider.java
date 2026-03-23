package logistics.transportation;

import models.transport.LandTransport;
import models.transport.Transport;
import models.transport.TransportType;

public class LandProvider extends TransportProvider {
    @Override
    boolean canHandle(String name) {
        return TransportType.LAND.matches(name);
    }

    @Override
    Transport createTransport(String name, double overheads, double speed) {
        return new LandTransport(name, overheads, speed);
    }
}
