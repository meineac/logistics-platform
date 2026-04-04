import logistics.management.StandardShipmentAssembler;
import logistics.transportation.AirProvider;
import logistics.transportation.LandProvider;
import logistics.transportation.WaterProvider;
import service.DataExportService;
import service.LogisticsService;
import ui.state.MainMenuState;
import ui.state.MenuContext;
import utils.helper.EntityAssembler;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        EntityAssembler assembler = new EntityAssembler(List.of(
                new AirProvider(),
                new LandProvider(),
                new WaterProvider()
        ));

        LogisticsService service = new LogisticsService(
                null,
                new StandardShipmentAssembler(),
                new DataExportService()
        );

        Scanner scanner = new Scanner(System.in);

        MenuContext uiContext = new MenuContext(new MainMenuState(), service, scanner, assembler);

        System.out.println("--- Logistics System Initialized ---");

        uiContext.run();

        scanner.close();
        System.out.println("System shut down successfully.");
    }
}