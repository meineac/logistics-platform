import logistics.management.StandardShipmentAssembler;
import logistics.transportation.AirProvider;
import logistics.transportation.LandProvider;
import logistics.transportation.WaterProvider;
import models.LogisticsData;
import models.cargo.CargoItem;
import models.delivery.PayloadBuilder;
import service.LogisticsService;
import utils.helper.EntityAssembler;
import utils.reader.XmlDataLoader;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // 1. Assemble parsers and providers
        EntityAssembler assembler = new EntityAssembler(List.of(
                new AirProvider(),
                new LandProvider(),
                new WaterProvider()
        ));
        var dataLoader = new XmlDataLoader(assembler);

        // 2. Create the main Service
        LogisticsService service = new LogisticsService(dataLoader, new StandardShipmentAssembler());

        // 3. Load the data
        service.loadSystemData("src/main/resources/logistic.xml");
        LogisticsData data = service.getAvailableData();

        System.out.println("--- Logistics System Initialized ---");
        System.out.println("Loaded Transports: " + data.transports().size());
        System.out.println("Loaded Cargo Types: " + data.cargoItems().size());

        // --- SIMULATE OPERATOR REQUEST ---

        // A. Select items from the database
        CargoItem electronics = findCargoByName(data, "Электроника");
        CargoItem clothing = findCargoByName(data, "Одежда");

        // B. The operator builds the shipment payload
        PayloadBuilder shipmentPayload = new PayloadBuilder()
                .addCargo(electronics, 150)
                .addCargo(clothing, 50);

        // C. The operator submits the request to the system
        double distanceKm = 1200.0;
        String chosenTransport = "Грузовик (Земля)";

        var shipment = service.arrangeShipment(shipmentPayload, chosenTransport, distanceKm);

        // D. Output the final calculations
        System.out.println("\n--- Shipment Details ---");
        System.out.println("Transport Used: " + chosenTransport);
        System.out.println("Distance: " + distanceKm + " km");
        System.out.printf("Total Delivery Cost: $%.2f%n", shipment.calculateTotalDeliveryCost());
        System.out.printf("Estimated Time: %.2f hours%n", shipment.calculateDeliveryTime());
    }

    // Helper method to look up items
    private static CargoItem findCargoByName(LogisticsData data, String name) {
        return data.cargoItems().stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cargo not found: " + name));
    }
}