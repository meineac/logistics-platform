package ui.state;

import models.delivery.Shipment;
import service.DataExportService.Format;

import java.util.List;

public class ExportResultsState implements MenuState {
    private final List<Shipment> sortedShipments;

    public ExportResultsState(List<Shipment> sortedShipments) {
        this.sortedShipments = sortedShipments;
    }

    @Override
    public void display(MenuContext context) {
        System.out.println("\n--- EXPORT RESULTS ---");
        System.out.println("1. Export as JSON");
        System.out.println("2. Export as CSV");
        System.out.println("3. Export as XML");
        System.out.println("0. Cancel");
    }

    @Override
    public void handleInput(MenuContext context, String input) {
        try {
            Format format = switch (input) {
                case "1" -> Format.JSON;
                case "2" -> Format.CSV;
                case "3" -> Format.XML;
                case "0" -> null;
                default -> throw new IllegalArgumentException("Invalid option");
            };

            if (format == null) {
                context.setState(new ShipmentResultsState(sortedShipments));
                return;
            }

            System.out.print("Enable ZIP Compression? (y/n): ");
            boolean compress = context.getScanner().nextLine().trim().equalsIgnoreCase("y");

            System.out.print("Enter password for Encryption (leave blank for none): ");
            String password = context.getScanner().nextLine().trim();

            System.out.print("Enter target file name (without extension): ");
            String fileName = "src/main/resources/exports/" + context.getScanner().nextLine().trim();

            System.out.println("\n[SYSTEM] Exporting " + sortedShipments.size() + " ordered records...");
            System.out.println("[SYSTEM] Format: " + format + " | Compressed: " + compress + " | Encrypted: " + !password.isBlank());

            context.getService().exportShipments(sortedShipments, fileName, format, compress, password);
            System.out.println("[SYSTEM] Export Complete!");

            context.setState(this);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}