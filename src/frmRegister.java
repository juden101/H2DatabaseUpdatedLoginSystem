import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frmRegister extends JFrame {
    private JPanel pnlContent;
    private JTextField usernameField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField; // Added Confirm Password field
    private JButton registerButton;

    public frmRegister() {
        setSize(600, 800);
        setContentPane(pnlContent);
        pnlContent.setPreferredSize(new Dimension(600, 800)); // Set preferred size for the main panel
        pack(); // Resize the frame to fit the preferred size of its components
        setTitle("Register Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);

        // Debug: Check if registerButton is null
        if (registerButton == null) {
            System.out.println("registerButton is null!"); // Debug print
        } else {
            System.out.println("registerButton is initialized!"); // Debug print
        }

        // Add action listener to the button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Register button clicked!"); // Debug print
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword()); // Added Confirm Password

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DBAccess db = DBAccess.getInstance();

        // Check if username or email already exists before inserting
        if (!db.isUserNameUnique(username)) {
            JOptionPane.showMessageDialog(this, "Username is already taken. Please choose another.");
            return;
        }

        if (!db.isEmailUnique(email)) {
            JOptionPane.showMessageDialog(this, "Email is already registered. Use a different email.");
            return;
        }

        // Create a new user
        User newUser = new User(username, firstName, lastName, email, password);

        // Insert user into the database
        boolean userAdded = db.addUser(newUser);
        if (userAdded) {
            JOptionPane.showMessageDialog(this, "User registered successfully!");
            dispose(); // Close the form after success
        } else {
            JOptionPane.showMessageDialog(this, "Failed to register user. Please try again.");
        }
    }

    public static void main(String[] args) {
        new frmRegister();
    }
}