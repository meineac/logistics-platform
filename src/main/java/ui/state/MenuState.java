package ui.state;

public interface MenuState {
    void display(MenuContext context);
    void handleInput(MenuContext context, String input);
}