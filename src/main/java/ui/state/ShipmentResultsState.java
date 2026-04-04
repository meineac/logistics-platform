package ui.state;

import logistics.management.filtering.MaxCostFilter;
import logistics.management.filtering.MaxTimeFilter;
import logistics.management.filtering.ShipmentFilter;
import logistics.management.sorting.ShipmentSorter;
import models.delivery.Shipment;

import java.util.Comparator;
import java.util.List;

public class ShipmentResultsState implements MenuState {
    private List<Shipment> shipments;
    private final ShipmentSorter sorter = new ShipmentSorter();

    public ShipmentResultsState(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    @Override
    public void display(MenuContext context) {
        System.out.println("\n--- SHIPMENT RESULTS (" + shipments.size() + " options) ---");
        for (int i = 0; i < shipments.size(); i++) {
            Shipment s = shipments.get(i);
            System.out.printf("%d. Transport: %-20s | Time: %6.1f hrs | Cost: $%.2f\n",
                    i + 1,
                    s.getTransport().getName(),
                    s.calculateDeliveryTime(),
                    s.calculateTotalDeliveryCost());
        }

        System.out.println("\nOptions:");
        System.out.println("1. Filter by Cost and Time");
        System.out.println("2. Sort by Cheapest Price");
        System.out.println("3. Sort by Fastest Time");
        System.out.println("8. Export results");
        System.out.println("9. Finish & Return to Menu");
    }

    @Override
    public void handleInput(MenuContext context, String input) {
        switch (input) {
            case "1" -> {
                System.out.print("Enter maximum budget: ");
                double maxCost = Double.parseDouble(context.getScanner().nextLine().trim());

                ShipmentFilter filterChain = new MaxCostFilter(maxCost);

                System.out.print("Enter maximum time: ");
                double time = Double.parseDouble(context.getScanner().nextLine().trim());
                filterChain.linkWith(new MaxTimeFilter(time));

                this.shipments = filterChain.filter(this.shipments);
                System.out.println("Filter applied.");
            }
            case "2" -> {
                sorter.addStrategy(Comparator.comparing(Shipment::calculateTotalDeliveryCost));
                sorter.sort(this.shipments);
                System.out.println("Sorted by price.");
            }
            case "3" -> {
                sorter.addStrategy(Comparator.comparing(Shipment::calculateDeliveryTime));
                sorter.sort(this.shipments);
                System.out.println("Sorted by time.");
            }
            case "8" -> context.setState(new ExportResultsState(this.shipments));
            case "9" -> context.setState(new DataLoadedMenuState());
            default -> System.out.println("Invalid option.");
        }
    }
}