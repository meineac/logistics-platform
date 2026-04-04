package ui.state;

import models.cargo.CargoItem;
import models.delivery.PayloadBuilder;
import models.transport.Transport;

import java.util.List;

public class ShipmentBuilderState implements MenuState {
    private enum Step { ADD_CARGO, SET_DISTANCE, SELECT_TRANSPORT }
    private Step currentStep = Step.ADD_CARGO;

    private final PayloadBuilder payloadBuilder = new PayloadBuilder();
    private double distance = 0;

    @Override
    public void display(MenuContext context) {
        switch (currentStep) {
            case ADD_CARGO -> {
                System.out.println("\n--- STEP 1: ADD CARGO TO PAYLOAD ---");
                List<CargoItem> items = context.getService().getAvailableData().cargoItems();
                for (int i = 0; i < items.size(); i++) {
                    System.out.printf("%d. %s (Weight: %.1f kg, Cost: $%.2f/kg)\n",
                            i + 1, items.get(i).getName(), items.get(i).getWeight(), items.get(i).getCost());
                }
                System.out.println("0. Continue to next step");
            }
            case SET_DISTANCE -> {
                System.out.println("\n--- STEP 2: SET DISTANCE ---");
                System.out.println("Enter delivery distance in kilometers:");
            }
            case SELECT_TRANSPORT -> {
                System.out.println("\n--- STEP 3: SELECT TRANSPORT METHOD ---");
                List<Transport> transports = context.getService().getAvailableData().transports();
                for (int i = 0; i < transports.size(); i++) {
                    System.out.printf("%d. %s\n", i + 1, transports.get(i).getName());
                }
                System.out.println("0. ANY");
            }
        }
    }

    @Override
    public void handleInput(MenuContext context, String input) {
        try {
            switch (currentStep) {
                case ADD_CARGO -> {
                    int choice = Integer.parseInt(input);
                    if (choice == 0) {
                        currentStep = Step.SET_DISTANCE;
                    } else {
                        List<CargoItem> items = context.getService().getAvailableData().cargoItems();
                        if (choice > 0 && choice <= items.size()) {
                            CargoItem selected = items.get(choice - 1);
                            System.out.print("Enter quantity for " + selected.getName() + ": ");

                            // Ask for quantity (synchronous blocking read for simplicity in the wizard)
                            int quantity = Integer.parseInt(context.getScanner().nextLine().trim());
                            payloadBuilder.addCargo(selected, quantity);
                            System.out.println("--> Added " + quantity + "x " + selected.getName() + " to shipment.");
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    }
                }
                case SET_DISTANCE -> {
                    distance = Double.parseDouble(input);
                    if (distance <= 0) throw new NumberFormatException();
                    currentStep = Step.SELECT_TRANSPORT;
                }
                case SELECT_TRANSPORT -> {
                    int choice = Integer.parseInt(input);
                    List<Transport> transports = context.getService().getAvailableData().transports();

                    if (choice == 0) {
                        var allOptions = context.getService().calculateAllShipmentOptions(payloadBuilder, distance);
                        context.setState(new ShipmentResultsState(allOptions));
                    } else if (choice > 0 && choice <= transports.size()) {
                        Transport selected = transports.get(choice - 1);
                        var shipment = context.getService().arrangeShipment(payloadBuilder, selected.getName(), distance);
                        context.setState(new ShipmentResultsState(List.of(shipment)));
                    } else {
                        System.out.println("Invalid transport choice.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }
}