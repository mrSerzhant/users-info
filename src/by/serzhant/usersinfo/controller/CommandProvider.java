package by.serzhant.usersinfo.controller;

import by.serzhant.usersinfo.service.command.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<String, Command> allCommands = new HashMap<>();

    public CommandProvider() {
        allCommands.put("1", new ViewAllUsers());
        allCommands.put("2", new AddUser());
        allCommands.put("3", new EditUser());
        allCommands.put("4", new DeleteUser());
        allCommands.put("invalidParam", new ExceptionCommand());
    }

    public Command getCommand(String inputString) {
        if (!allCommands.containsKey(inputString)) {
            return allCommands.get("invalidParam");
        }

        return allCommands.get(inputString);
    }
}
