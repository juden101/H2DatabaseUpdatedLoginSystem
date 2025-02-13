package models;

public class Admin extends User {

    public Admin(String userName, String firstName, String lastName, String email, String password) {
        super(userName, firstName, lastName, email, password);
    }

    public Admin(int id, String userName, String firstName, String lastName, String email, String password) {
        super(id, userName, firstName, lastName, email, password);
    }

    public void addRestaurant(Restaurant rest) {
        // Add a new restaurant to the database
    }

    public void removeRestaurant(int id) {
        // Remove a restaurant from the database
    }

    public String getRole() {
        return "admin";
    }
}
