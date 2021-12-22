package by.serzhant.usersinfo.service.command;

import by.serzhant.usersinfo.dal.DataReader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewAllUsers implements Command {
    private static final String DATA_FILE_PATH = "src/resources/data.xml";

    @Override
    public Object execute() {
        DataReader dataReader = new DataReader();

        Path path = Paths.get(DATA_FILE_PATH);

        if(dataReader.readDataFile(path).isEmpty()){
            return "Список пуст, добавьте пользователей";
        }

        return dataReader.readDataFile(path);
    }
}
