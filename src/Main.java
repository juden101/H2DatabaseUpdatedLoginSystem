import models.DBAccess;
import models.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {

// Get the models.DBAccess Singleton instance (this initializes the DB)
        DBAccess db = DBAccess.getInstance();

        // Fetch and display users before adding a new one
        System.out.println("\n Fetching users BEFORE adding a new one...");
        List<User> usersBefore = db.getAllUsers();
        for (User user : usersBefore) {
            System.out.println(user);
        }


        // Fetch and display users after adding the new one
        System.out.println("\n Fetching users AFTER adding a new one...");
        List<User> usersAfter = db.getAllUsers();
        for (User user : usersAfter) {
            System.out.println(user);
        }
    }

}