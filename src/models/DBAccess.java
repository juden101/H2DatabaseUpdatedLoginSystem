package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBAccess {

    // private static final String URL = "jdbc:h2:tcp://localhost:9092/~/DineDashH2.db";
    //private static final String URL = "jdbc:h2:~/DineDashH2_c";
    private static final String URL = "jdbc:h2:file:./data/dinedashdb2;AUTO_SERVER=TRUE";

    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private static DBAccess instance;  // Singleton instance
    private Connection connection;     // Database connection

    // Private constructor to enforce singleton pattern
    private DBAccess() {
        connect();
    }

    // Get Singleton instance
    public static DBAccess getInstance() {
        if (instance == null) {
            instance = new DBAccess();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            // Check if the connection is still valid
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error obtaining database connection: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    // Establish a connection to the database
    private void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✅ Database connection established.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public User authenticateUser(String username, String password) throws SQLException {
       //connect();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("✅ authentication successful!");
                    switch (rs.getString("role").toLowerCase()) {
                        case "admin":
                            System.out.println("✅ Admin authenticated!");

                            User u = new Admin(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("password") // Not masking password
                            );

                            return u;// Not masking password

                        case "staff":
                            return new Staff(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("password"),
                                    rs.getInt("restaurant_id")// Not masking password
                            );
                        case "customer":
                            return new Customer(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("password") // Not masking password
                            );
                        default:
                            return null;
                    }
                }
            } catch (SQLException e) {
                System.err.println("❌ Authentication error: " + e.getMessage());
                e.printStackTrace();
            }
            return null; // Return null if authentication fails
        }

    }

    // Fetch all users from the database
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, first_name, last_name, role, email,restaurant_id FROM users";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {

                switch (rs.getString("role").toLowerCase()) {
                    case "admin":
                        users.add(new Admin(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email"),
                                rs.getString("password") // Not masking py
                        ));
                        break;
                    case "staff":
                        users.add(new Staff(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getInt("restaurant_id")// Not masking p
                        ));
                        break;


                    case "customer":
                        users.add(new Customer(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email"),
                                rs.getString("password") // Not masking password
                        ));
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Fetch users error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    // Check if username is unique
    public boolean isUserNameUnique(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) == 0; // True if username doesn't exist
        } catch (SQLException e) {
            System.err.println("❌ Username check error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Add user with role
    public boolean addUser(User user, String role) {
        String sql = "INSERT INTO users (username, first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, role.toLowerCase()); // Store role in lowercase

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Add user error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public User getUser(String username)  throws SQLException{
        String sql = "SELECT * FROM users WHERE username = ? ";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery() ;

                if (rs.next()) {
                    switch (rs.getString("role").toLowerCase()) {
                        case "admin":
                            return new Admin(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("password") // Not masking password
                            );
                        case "staff":
                            return new Staff(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("password") ,
                                    rs.getInt("restaurant_id")// Not masking password
                            );
                        case "customer":
                            return new Customer(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("password") // Not masking password
                            );
                        default:
                            return null;
                    }
                }

        } catch (SQLException e) {
            System.err.println("❌ Authentication error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
       return null;
    }

    public User getUserById(int uid) {
        String sql = "SELECT * FROM users WHERE id = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, uid);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    switch (rs.getString("role").toLowerCase()) {
                        case "admin":
                            return new Admin(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("password") // Not masking password
                            );
                        case "staff":
                            return new Staff(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("password") ,
                                    rs.getInt("restaurant_id")// Not masking password
                            );
                    }
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Authentication error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ?, first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Update user error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Delete user error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}