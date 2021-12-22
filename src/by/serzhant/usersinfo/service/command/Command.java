package by.serzhant.usersinfo.service.command;

import by.serzhant.usersinfo.exception.ExecuteException;

public interface Command {
    Object execute() throws ExecuteException;
}
