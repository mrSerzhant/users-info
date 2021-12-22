package by.serzhant.usersinfo.controller;

public class Controller {
    private final CommandProvider provider = new CommandProvider();

    public Object executeTask(String request) {
        Object response;

        try {
            response = provider.getCommand(request).execute();
        } catch (Exception e) {
            response = e.getMessage();
        }

        return response;
    }
}
