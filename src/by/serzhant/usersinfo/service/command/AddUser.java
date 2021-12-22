package by.serzhant.usersinfo.service.command;

import by.serzhant.usersinfo.dal.DataReader;
import by.serzhant.usersinfo.dal.DataWriter;
import by.serzhant.usersinfo.entity.Role;
import by.serzhant.usersinfo.entity.User;
import by.serzhant.usersinfo.service.validator.Validator;
import by.serzhant.usersinfo.view.console_processing.ConsoleReader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddUser implements Command {
    private static final String DATA_FILE_PATH = "src/resources/data.xml";

    @Override
    public Object execute() {
        File file = new File("src/resources");

        if(!file.exists())
        {
            file.mkdir();
        }

        DataReader dataReader = new DataReader();
        Path path = Paths.get(DATA_FILE_PATH);

        List<User> usersList = dataReader.readDataFile(path);

        ConsoleReader consoleReader = new ConsoleReader();
        Validator validator = new Validator();

        String firstName;

        do {
            System.out.println("Введите имя");
            firstName = consoleReader.readInputValue();

        } while (firstName.isEmpty());

        String lastName ;

        do {
            System.out.println("Введите фамилию");
            lastName = consoleReader.readInputValue();

        } while (lastName.isEmpty());

        String email;

        while (true){
            System.out.println("Введите email");

            email = consoleReader.readInputValue();

            if(validator.isValidEmail(email)){
                break;
            }

            System.out.println("Неверный email");
        }

        System.out.println("Добавьте роль");

        System.out.println("1 - " + Role.USER);
        System.out.println("2 - " + Role.CUSTOMER);
        System.out.println("3 - " + Role.ADMIN);
        System.out.println("4 - " + Role.PROVIDER);
        System.out.println("5 - " + Role.SUPER_ADMIN);

        List<Role> tempList = new ArrayList<>();

        boolean flag = true;

        while (flag) {
            switch (consoleReader.readInputValue()) {
                case "1":
                    tempList.add(Role.USER);
                    flag = false;
                    break;
                case "2":
                    tempList.add(Role.CUSTOMER);
                    flag = false;
                    break;
                case "3":
                    tempList.add(Role.ADMIN);
                    flag = false;
                    break;
                case "4":
                    tempList.add(Role.PROVIDER);
                    flag = false;
                    break;
                case "5":
                    tempList.add(Role.SUPER_ADMIN);
                    flag = false;
                    break;
                default:
                    System.out.println("Ошибка выбора");
            }
        }

        String phoneNumber;

        while (true){
            System.out.println("Добавьте номер телефона");

            phoneNumber = consoleReader.readInputValue();

            if(validator.isValidPhoneNumber(phoneNumber)){
                break;
            }

            System.out.println("Неверный номер телефона");
        }

        User user = new User(firstName,lastName,email,phoneNumber, tempList.get(0));
        usersList.add(user);

        DataWriter dataWriter = new DataWriter();
        dataWriter.writeDataFile(usersList);

        return "Пользователь успешно добавлен";
    }
}
