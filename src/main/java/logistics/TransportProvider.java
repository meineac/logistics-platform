package logistics;

import models.transport.Transport;

public abstract class TransportProvider {
    public Transport getTransport(String name, double overheads, double speed) {
        if (canHandle(name)) {
            return createTransport(name, overheads, speed);
        }
        return null;
    }

    abstract boolean canHandle(String transportName);

    abstract Transport createTransport(String name, double overheads, double speed);
}
