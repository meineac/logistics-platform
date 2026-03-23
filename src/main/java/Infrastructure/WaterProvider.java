package Infrastructure;

import models.transport.Transport;
import models.transport.WaterTransport;

public class WaterProvider extends  TransportProvider {
    @Override
    public Transport createTransport(String name, double overheads, double speed) {
        return new WaterTransport(name, overheads, speed);
    }
}
