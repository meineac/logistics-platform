package Infrastructure;

import models.transport.Transport;

public abstract class TransportProvider {
    public abstract Transport createTransport(String name, double overheads, double speed);
}
