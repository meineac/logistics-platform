package Infrastructure;

import models.transport.AirTransport;
import models.transport.Transport;

public class AirProvider extends TransportProvider {
    @Override
    public Transport createTransport(String name, double overheads, double speed) {
        return new AirTransport(name, overheads, speed);
    }
}
