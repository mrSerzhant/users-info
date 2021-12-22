package by.serzhant.usersinfo.view.console_processing;

import by.serzhant.usersinfo.entity.User;

import java.util.List;

public class ConsolePrinter {
    private static final String LIST_ALL_USERS = "Список пользователей";
    private static final String ADD_USER = "Добавить пользователя";
    private static final String EDIT_USER = "Изменить пользователя";
    private static final String DELETE_USER = "Удалить пользователя";
    private static final String EXIT = "Выход";

    public void printMenu() {
        System.out.println("1 - " + LIST_ALL_USERS);
        System.out.println("2 - " + ADD_USER);
        System.out.println("3 - " + EDIT_USER);
        System.out.println("4 - " + DELETE_USER);
        System.out.println("0 - " + EXIT);
    }

    public void printResult(Object inputObject) {
        if (inputObject instanceof Iterable) {
            List<User> userList = (List<User>) inputObject;

            for (int i = 0; i < userList.size(); i++) {
                System.out.println(i + 1 + " - " + userList.get(i));
            }
        } else {
            System.out.println(inputObject.toString());
        }
    }

    public void printExitMessage() {
        System.out.println("Завершение работы");
    }
}
