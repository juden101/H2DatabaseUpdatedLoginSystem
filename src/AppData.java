import models.Users;

public class AppData {

    private static AppData instance;

    private Users users;


    private AppData() {

        users = new Users();

    }


    public static AppData getInstance() {

        if (instance == null) {

            instance = new AppData();

        }

        return instance;

    }


    public Users getUsers() {

        return users;

    }
}