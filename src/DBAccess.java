import org.h2.tools.RunScript;
import org.h2.tools.Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DBAccess {

    private static final String URL = "jdbc:h2:tcp://localhost:9092/~/DD.db";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private static DBAccess instance; // Singleton instance
    private Connection connection; // Connection instance

    // Private constructor (No connection opened immediately)
    private DBAccess() {
        initDB();
    }

    // Public method to get the single instance
    public static DBAccess getInstance() {
        if (instance == null) {
            instance = new DBAccess();
        }
        return instance;
    }

    // ✅ Runs once when the Singleton is created
    private void initDB() {
        startH2Server();   // Start H2 server
        createSchema();   // Create users table if not exists
        seedData(); // Insert admin user if table is empty
        fetchUsers();    // Fetch users for verification
    }

    private void seedData() {
        String checkSql = "SELECT COUNT(*) FROM users";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(checkSql);
             ResultSet rs = stmt.executeQuery()) {

            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                System.out.println("🔍 No users found, inserting initial data...");
                loadScript("src/scripts/insert_sql.sql");
            } else {
                System.out.println(" Users already exist, skipping insert.");
            }
        } catch (SQLException e) {
            System.err.println("Error checking user count: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void createSchema() {
        loadScript("src/scripts/create_db.sql");
    }

    // Ensure connection is always valid before returning
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish database connection.");
        }
        return connection;
    }

    /**
     * Loads and executes an SQL script from a file.
     *
     * @param scriptPath Path to the SQL file (e.g., "src/main/resources/schema.sql")
     */
    public void loadScript(String scriptPath) {
        try (FileReader reader = new FileReader(scriptPath)) {
            RunScript.execute(getConnection(), reader);
            System.out.println("SQL script executed successfully!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException | IOException e) {
            System.err.println("Error executing script: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // User found, authentication successful
                }
            }

        } catch (SQLException e) {
            System.err.println("Error during authentication: " + e.getMessage());
            e.printStackTrace();
        }

        return false; // User not found or error occurred
    }

//    public void loadScript(String scriptPath) {
//        try (Connection conn = getConnection();
//             BufferedReader reader = new BufferedReader(new FileReader(scriptPath));
//             Statement stmt = conn.createStatement()) {
//
//            StringBuilder sql = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sql.append(line);
//                if (line.trim().endsWith(";")) {  // Execute when a statement is complete
//                    stmt.execute(sql.toString());
//                    sql.setLength(0);  // Clear buffer
//                }
//            }
//            System.out.println("✅ SQL script executed successfully: " + scriptPath);
//
//        } catch (SQLException | IOException e) {
//            System.err.println("❌ Error executing script: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public void fetchUsers() {
        String sql = "SELECT * FROM users";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Users in the database:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                System.out.println("User ID: " + id + ", UserName: " + name);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void startH2Server() {
        try {
            // H2 Server instance
            Server h2Server = Server.createTcpServer("-tcpAllowOthers", "-tcp", "-web").start();
            System.out.println("✅ H2 Server started at: " + h2Server.getURL());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to start H2 Server.");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id,username, first_name, last_name, email, password FROM users";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public boolean isUserNameUnique(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Username exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; // Username is unique
    }

    public boolean isEmailUnique(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Email exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; // Email is unique
    }


    public boolean addUser(User user) {
        String sql = "INSERT INTO users (username, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if the user was successfully added
        } catch (SQLException e) {
            System.err.println("Error inserting user into DB: " + e.getMessage());
            return false;
        }
    }





    // Method to close the connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


