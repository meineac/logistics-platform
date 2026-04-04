package ui.state;

import service.LogisticsService;
import utils.helper.EntityAssembler;

import java.util.Scanner;

public class MenuContext {
    private MenuState currentState;
    private final EntityAssembler assembler;
    private final LogisticsService service;
    private final Scanner scanner;
    private boolean isRunning = true;

    public MenuContext(MenuState initialState, LogisticsService service, Scanner scanner, EntityAssembler assembler) {
        this.currentState = initialState;
        this.service = service;
        this.scanner = scanner;
        this.assembler = assembler;
    }

    public void setState(MenuState state) {
        this.currentState = state;
    }

    public LogisticsService getService() { return service; }
    public Scanner getScanner() { return scanner; }
    public EntityAssembler getAssembler() { return assembler; }

    public void stop() { this.isRunning = false; }

    public void run() {
        while (isRunning) {
            System.out.println("\n=================================");
            currentState.display(this);
            System.out.print("\nChoose an option: ");
            String input = scanner.nextLine().trim();
            currentState.handleInput(this, input);
        }
    }
}