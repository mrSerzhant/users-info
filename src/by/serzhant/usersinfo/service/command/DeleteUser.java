package by.serzhant.usersinfo.service.command;

import by.serzhant.usersinfo.dal.DataReader;
import by.serzhant.usersinfo.dal.DataWriter;
import by.serzhant.usersinfo.entity.User;
import by.serzhant.usersinfo.exception.ExecuteException;
import by.serzhant.usersinfo.service.validator.Validator;
import by.serzhant.usersinfo.view.console_processing.ConsolePrinter;
import by.serzhant.usersinfo.view.console_processing.ConsoleReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DeleteUser implements Command {
    private static final String DATA_FILE_PATH = "src/resources/data.xml";

    @Override
    public Object execute() throws ExecuteException {
        DataReader dataReader = new DataReader();
        Path path = Paths.get(DATA_FILE_PATH);

        List<User> usersList = dataReader.readDataFile(path);

        if (usersList.isEmpty()) {
            return "Списко пуст, добавьте нового пользователя";
        }

        System.out.println("Выберите пользователя");

        ConsolePrinter consolePrinter = new ConsolePrinter();
        consolePrinter.printResult(usersList);

        ConsoleReader consoleReader = new ConsoleReader();
        DataWriter dataWriter = new DataWriter();
        Validator validator = new Validator();

        while (true) {
            String inputValue = consoleReader.readInputValue();

            if (validator.isNumber(inputValue)) {
                int parsedInputValue = Integer.parseInt(inputValue);

                if (parsedInputValue > 0 && parsedInputValue < usersList.size() + 1) {
                    usersList.remove(parsedInputValue - 1);
                    dataWriter.writeDataFile(usersList);
                    return "Удаление завершено завершено";
                }
            }
        }

    }
}
