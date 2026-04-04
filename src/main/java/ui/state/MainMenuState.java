package ui.state;

import utils.loader.CsvDataLoader;
import utils.loader.DataLoader;
import utils.loader.JsonDataLoader;
import utils.loader.XmlDataLoader;

public class MainMenuState implements MenuState {
    public MainMenuState() {
    }

    @Override
    public void display(MenuContext context) {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Load System Data");
        System.out.println("0. Exit");
    }

    @Override
    public void handleInput(MenuContext context, String input) {
        switch (input) {
            case "1" -> {
                System.out.print("Select format (JSON, XML, CSV): ");
                String format = context.getScanner().nextLine().trim().toUpperCase();

                try {
                    // Create the correct strategy based on user input
                    DataLoader loader = switch (format) {
                        case "JSON" -> new JsonDataLoader(context.getAssembler());
                        case "XML" -> new XmlDataLoader(context.getAssembler());
                        case "CSV" -> new CsvDataLoader(context.getAssembler());
                        default -> throw new IllegalArgumentException("Unsupported format");
                    };

                    // Set the strategy in the service
                    context.getService().setDataLoader(loader);

                    System.out.print("Enter file path (e.g., src/main/resources/imports/logistic." + format.toLowerCase() + "): ");
                    String path = context.getScanner().nextLine().trim();

                    // Load the data
                    context.getService().loadSystemData(path);
                    System.out.println("Data loaded successfully!");

                    // Transition deeper into the menu tree now that data is available
                    context.setState(new DataLoadedMenuState());

                } catch (Exception e) {
                    System.out.println("Error loading data: " + e.getMessage());
                }
            }
            case "0" -> {
                System.out.println("Goodbye!");
                context.stop(); // Breaks the loop in App.java
            }
            default -> System.out.println("Invalid option.");
        }
    }
}