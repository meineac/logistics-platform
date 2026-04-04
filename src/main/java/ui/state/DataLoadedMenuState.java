package ui.state;

public class DataLoadedMenuState implements MenuState {
    @Override
    public void display(MenuContext context) {
        System.out.println("--- ACTIVE SESSION ---");
        System.out.println("1. View Cargo and Transports");
        System.out.println("2. Arrange a Shipment");
        System.out.println("9. Back to Main Menu");
    }

    @Override
    public void handleInput(MenuContext context, String input) {
        switch (input) {
            case "1" -> {
                var data = context.getService().getAvailableData();
                System.out.println("Transports: " + data.transports().size());
                for (var transport : data.transports()) {
                    System.out.println("\tName: " + transport.getName());
                    System.out.println("\tOverheads: " + transport.getOverheads());
                    System.out.println("\tSpeed: " + transport.getSpeed());
                }
                System.out.println("Cargos: " + data.cargoItems().size());
                for (var cargo : data.cargoItems()) {
                    System.out.println("\tName: " + cargo.getName());
                    System.out.println("\tWeight: " + cargo.getWeight());
                    System.out.println("\tCost: " + cargo.getCost());
                }
            }
            case "2" -> context.setState(new ShipmentBuilderState());
            case "9" -> context.setState(new MainMenuState());
            default -> System.out.println("Invalid option.");
        }
    }
}