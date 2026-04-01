package utils.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.LogisticsData;
import models.cargo.CargoItem;
import models.transport.Transport;
import utils.helper.EntityAssembler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonDataLoader implements DataLoader {
    private final EntityAssembler assembler;

    public JsonDataLoader(EntityAssembler assembler) {
        this.assembler = assembler;
    }

    @Override
    public LogisticsData load(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(filePath));

            JsonNode cargoArrayNode = rootNode.get("cargoItems");
            JsonNode transportArrayNode = rootNode.get("transports");

            List<CargoItem> cargoItems = parseCargos(cargoArrayNode);
            List<Transport> transports = parseTransports(transportArrayNode);

            return new LogisticsData(cargoItems, transports);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON: " + filePath, e);
        }
    }

    private List<Transport> parseTransports(JsonNode transportArrayNode) {
        List<Transport> transports = new ArrayList<>();

        for (var transport : transportArrayNode) {
            if (transport.isNull() || !transport.isObject()) {
                throw new IllegalArgumentException(
                        "Invalid JSON format: transport item cannot be null and must be an object. " +
                                "Found: " + transport
                );
            }

            if (!transport.hasNonNull("name") || !transport.hasNonNull("overheads") || !transport.hasNonNull("speed")) {
                throw new IllegalArgumentException(
                        "Invalid JSON format:  transport item is missing required fields (name, overheads, speed). " +
                                "Problematic node:\n" + transport.toPrettyString()
                );
            }

            transports.add(assembler.assembleTransport(
                    transport.get("name").asText(),
                    transport.get("overheads").asDouble(),
                    transport.get("speed").asDouble()
            ));
        }
        return transports;
    }

    private List<CargoItem> parseCargos(JsonNode cargoArrayNode) {
        List<CargoItem> cargoItems = new ArrayList<>();

        for (var cargo : cargoArrayNode) {
            if (cargo.isNull() || !cargo.isObject()) {
                throw new IllegalArgumentException(
                        "Invalid JSON format: cargo item cannot be null and must be an object. " +
                                "Found: " + cargo
                );
            }

            if (!cargo.hasNonNull("name") || !cargo.hasNonNull("weight") || !cargo.hasNonNull("cost")) {
                throw new IllegalArgumentException(
                        "Invalid JSON format: cargo item is missing required fields (name, weight, cost). " +
                                "Problematic node:\n" + cargo.toPrettyString()
                );
            }

            cargoItems.add(assembler.assembleCargo(
                    cargo.get("name").asText(),
                    cargo.get("weight").asDouble(),
                    cargo.get("cost").asDouble()
            ));

        }
        return cargoItems;
    }
}
