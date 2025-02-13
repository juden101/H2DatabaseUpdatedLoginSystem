package models;

public class Staff extends User{

    private int restId;

    public Staff(String userName, String firstName, String lastName, String email, String password, int restId) {
        super(userName, firstName, lastName, email, password);
        this.restId=restId;
    }

    public Staff(int id, String userName, String firstName, String lastName, String email, String password, int restId) {
        super(id, userName, firstName, lastName, email, password);
        this.restId=restId;
    }

    public void setMenu(Menu menu) {
        // Set the menu for the restaurant
    }

    public String getRole() {
        return "staff";
    }
}
