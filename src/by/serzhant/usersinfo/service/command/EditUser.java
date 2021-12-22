package by.serzhant.usersinfo.service.command;

import by.serzhant.usersinfo.dal.DataReader;
import by.serzhant.usersinfo.dal.DataWriter;
import by.serzhant.usersinfo.entity.Role;
import by.serzhant.usersinfo.entity.User;
import by.serzhant.usersinfo.service.validator.Validator;
import by.serzhant.usersinfo.view.console_processing.ConsolePrinter;
import by.serzhant.usersinfo.view.console_processing.ConsoleReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EditUser implements Command {
    private static final String DATA_FILE_PATH = "src/resources/data.xml";
    private static final String BAD_CHOICE_MESSAGE = "Недопустимое сочетание ролей";

    @Override
    public Object execute() {
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
                    User user = usersList.get(parsedInputValue - 1);
                    editUser(user);
                    dataWriter.writeDataFile(usersList);
                    return "Редактирование завершено";
                }
            }
            System.out.println("Неверный выбор, повторите");
        }
    }

    private void editUser(User user) {
        final String SUCCESS_MESSAGE = "Изменение выполнено";

        ConsoleReader consoleReader = new ConsoleReader();
        Validator validator = new Validator();

        while (true) {
            System.out.println("1 - Изменить имя");
            System.out.println("2 - Изменить фамилию");
            System.out.println("3 - Изменить email");
            System.out.println("4 - Изменить роль");
            System.out.println("5 - Добавить роль");
            System.out.println("6 - Изменить номер телефона");
            System.out.println("7 - Добавить номер телефона");
            System.out.println("8 - Закончить изменения");

            switch (consoleReader.readInputValue()) {
                case "1":
                    while (true) {
                        System.out.println("Введите новое имя");

                        String newFirstName = consoleReader.readInputValue();

                        if (!newFirstName.isEmpty()) {
                            user.setFirstName(newFirstName);
                            break;
                        }
                    }

                    System.out.println(SUCCESS_MESSAGE);
                    break;
                case "2":
                    while (true) {
                        System.out.println("Введите новую фамилию");

                        String newLastName = consoleReader.readInputValue();

                        if (!newLastName.isEmpty()) {
                            user.setLastName(newLastName);
                            break;
                        }
                    }

                    System.out.println(SUCCESS_MESSAGE);
                    break;
                case "3":
                    String newEmail;

                    while (true) {
                        System.out.println("Введите email");

                        newEmail = consoleReader.readInputValue();

                        if (validator.isValidEmail(newEmail)) {
                            user.setEmail(newEmail);
                            System.out.println(SUCCESS_MESSAGE);
                            break;
                        }

                        System.out.println("Неверный email");
                    }
                    break;
                case "4":
                    editRole(user);
                    System.out.println("Роль изменена");
                    break;
                case "5":
                    if (user.getRoles().size() >= 2) {
                        System.out.println("Установлено максимальное количество ролей");
                        break;
                    }

                    addRole(user);
                    System.out.println("Роль добавлена");
                    break;
                case "6":
                    if (user.getListPhoneNumber().isEmpty()) {
                        System.out.println("Список пуст, добавьте номер");
                        break;
                    }

                    editPhoneNumber(user);
                    System.out.println("Номер телефона добавлен");
                    break;
                case "7":
                    if (user.getListPhoneNumber().size() >= 3) {
                        System.out.println("Установлено максимальное номеров телефона");
                        break;
                    }

                    addPhoneNumber(user);
                    System.out.println("Номер телефона добавлен");
                    break;
                case "8":
                    return;
            }
        }
    }

    private void editPhoneNumber(User user) {
        System.out.println("Текущие номера телефонанов");

        for (int i = 0; i < user.getListPhoneNumber().size(); i++) {
            System.out.println(i + 1 + " " + user.getListPhoneNumber().get(i));
        }

        ConsoleReader consoleReader = new ConsoleReader();

        String phoneNumeral = "";
        boolean flag = true;

        while (flag) {
            System.out.println("Выберите изменяемый номер");

            String inputValue = consoleReader.readInputValue();

            if (inputValue.isEmpty()) {
                System.out.println("Неверное значение");
                continue;
            }

            for (int i = 0; i < user.getListPhoneNumber().size(); i++) {
                if (inputValue.equals(String.valueOf(i + 1))) {
                    phoneNumeral = inputValue;
                    break;
                }
            }

            if(phoneNumeral.isEmpty()){
                System.out.println("Неверный выбор");
                continue;
            }

            System.out.println("Введите новый номер телефона");

            Validator validator = new Validator();

            String newPhoneNumber = consoleReader.readInputValue();

            if (validator.isValidPhoneNumber(newPhoneNumber)) {
                user.changePhoneNumber(Integer.parseInt(phoneNumeral) - 1, newPhoneNumber);
                flag = false;

            }else {
                System.out.println("Неверный номер телефона");
            }
        }
    }

    private void addPhoneNumber(User user) {
        ConsoleReader consoleReader = new ConsoleReader();
        Validator validator = new Validator();

        while (true) {
            System.out.println("Введите новый номер телефона");

            String inputValue = consoleReader.readInputValue();

            if (validator.isValidPhoneNumber(inputValue)) {
                user.addPhoneNumber(inputValue);
                return;
            }

            System.out.println("Неверный номер телефона");
        }
    }

    private void addRole(User user) {
        ConsoleReader consoleReader = new ConsoleReader();
        Validator validator = new Validator();

        while (true) {
            System.out.println("Выберите новую роль");
            System.out.println("1 - " + Role.USER);
            System.out.println("2 - " + Role.CUSTOMER);
            System.out.println("3 - " + Role.ADMIN);
            System.out.println("4 - " + Role.PROVIDER);
            System.out.println("5 - " + Role.SUPER_ADMIN);
            System.out.println("0 - " + "Выход");

            switch (consoleReader.readInputValue()) {
                case "1":
                    if (validator.isValidRole(user.getRoles(), Role.USER)) {
                        user.addRole(Role.USER);
                        return;
                    }

                    System.out.println(BAD_CHOICE_MESSAGE);
                    break;
                case "2":
                    if (validator.isValidRole(user.getRoles(), Role.CUSTOMER)) {
                        user.addRole(Role.CUSTOMER);
                        return;
                    }

                    System.out.println(BAD_CHOICE_MESSAGE);
                    break;
                case "3":
                    if (validator.isValidRole(user.getRoles(), Role.ADMIN)) {
                        user.addRole(Role.ADMIN);
                        return;
                    }

                    System.out.println(BAD_CHOICE_MESSAGE);
                    break;
                case "4":
                    if (validator.isValidRole(user.getRoles(), Role.PROVIDER)) {
                        user.addRole(Role.PROVIDER);
                        return;
                    }

                    System.out.println(BAD_CHOICE_MESSAGE);
                    break;
                case "5":
                    if (user.getRoles().isEmpty()) {
                        user.addRole(Role.SUPER_ADMIN);
                        return;
                    }

                    System.out.println(BAD_CHOICE_MESSAGE);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Ошибка выбора");
                    break;
            }
        }
    }

    private void editRole(User user) {
        System.out.println("Текущие роли");

        for (int i = 0; i < user.getRoles().size(); i++) {
            System.out.println(i + 1 + " " + user.getRoles().get(i));
        }

        ConsoleReader consoleReader = new ConsoleReader();

        String roleNumber = "";
        boolean flag = true;

        while (flag) {
            System.out.println("Выберите изменяемую роль");

            String inputValue = consoleReader.readInputValue();

            if (inputValue.isEmpty()) {
                System.out.println("Неверное значение");
                continue;
            }

            for (int i = 0; i < user.getRoles().size(); i++) {
                if (inputValue.equals(String.valueOf(i + 1))) {
                    roleNumber = inputValue;
                    flag = false;
                    break;
                }
            }

            if (roleNumber.isEmpty()) {
                System.out.println("Неверный выбор");
            }
        }

        Validator validator = new Validator();

        while (true) {
            System.out.println("Выберите новую роль");
            System.out.println("1 - " + Role.USER);
            System.out.println("2 - " + Role.CUSTOMER);
            System.out.println("3 - " + Role.ADMIN);
            System.out.println("4 - " + Role.PROVIDER);
            System.out.println("5 - " + Role.SUPER_ADMIN);

            switch (consoleReader.readInputValue()) {
                case "1":
                    if (validator.isValidRole(user.getRoles(), Role.USER, roleNumber)) {
                        user.changeRole(Integer.parseInt(roleNumber) - 1, Role.USER);
                        return;
                    }

                    System.out.println(BAD_CHOICE_MESSAGE);
                    break;
                case "2":
                    if (validator.isValidRole(user.getRoles(), Role.CUSTOMER, roleNumber)) {
                        user.changeRole(Integer.parseInt(roleNumber) - 1, Role.CUSTOMER);
                        return;
                    }

                    System.out.println(BAD_CHOICE_MESSAGE);
                    break;
                case "3":
                    if (validator.isValidRole(user.getRoles(), Role.ADMIN, roleNumber)) {
                        user.changeRole(Integer.parseInt(roleNumber) - 1, Role.ADMIN);
                        return;
                    }

                    System.out.println(BAD_CHOICE_MESSAGE);
                    break;
                case "4":
                    if (validator.isValidRole(user.getRoles(), Role.PROVIDER, roleNumber)) {
                        user.changeRole(Integer.parseInt(roleNumber) - 1, Role.PROVIDER);
                        return;
                    }

                    System.out.println(BAD_CHOICE_MESSAGE);
                    break;
                case "5":
                    if (validator.isValidRole(user.getRoles(), Role.SUPER_ADMIN, roleNumber)) {
                        user.changeRole(Integer.parseInt(roleNumber) - 1, Role.SUPER_ADMIN);
                        return;
                    }

                    System.out.println(BAD_CHOICE_MESSAGE);
                    break;
                default:
                    System.out.println("Ошибка выбора");
                    break;
            }
        }
    }
}
