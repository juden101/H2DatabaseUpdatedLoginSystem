package models;

public class Customer extends User {
    public Customer(String userName, String firstName, String lastName, String email, String password) {
        super(userName, firstName, lastName, email, password);
    }

    public Customer(int id, String userName, String firstName, String lastName, String email, String password) {
        super(id, userName, firstName, lastName, email, password);
    }

    public String getRole() {
        return "customer";
    }
}
