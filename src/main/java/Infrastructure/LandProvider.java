package Infrastructure;

import models.transport.LandTransport;
import models.transport.Transport;

public class LandProvider extends TransportProvider {
    @Override
    public Transport createTransport(String name, double overheads, double speed) {
        return new LandTransport(name, overheads, speed);
    }
}
