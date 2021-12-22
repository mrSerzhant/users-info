package by.serzhant.usersinfo.service.command;

import by.serzhant.usersinfo.exception.ExecuteException;

public class ExceptionCommand implements Command {
    @Override
    public Object execute() throws ExecuteException {
        throw new ExecuteException("Неверная команда");
    }
}
