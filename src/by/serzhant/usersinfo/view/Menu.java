package by.serzhant.usersinfo.view;

import by.serzhant.usersinfo.controller.Controller;
import by.serzhant.usersinfo.view.console_processing.ConsolePrinter;
import by.serzhant.usersinfo.view.console_processing.ConsoleReader;

public class Menu {

    public Menu() {
        run();
    }

    private void run() {
        ConsoleReader consoleReader = new ConsoleReader();
        ConsolePrinter consolePrinter = new ConsolePrinter();

        Controller controller = new Controller();

        while (true) {
            consolePrinter.printMenu();

            String inputValue = consoleReader.readInputValue();

            if (inputValue.equals("0") || inputValue.isEmpty()) {
                consolePrinter.printExitMessage();
                return;
            }

            Object response = controller.executeTask(inputValue);

            consolePrinter.printResult(response);
        }
    }
}
