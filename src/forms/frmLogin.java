package forms;

import models.DBAccess;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class frmLogin extends JFrame {
    private JPanel pnlContent;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;


    public frmLogin() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(pnlContent);
        setSize(600, 600);
        pnlContent.setPreferredSize(new Dimension(600, 600)); // Set preferred size for the main panel
        pack(); // Resize the frame to fit the preferred size of its components

        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Assuming models.DBAccess is a class that handles database operations
        DBAccess db = DBAccess.getInstance();

        User loggedInUser= null;
        try {
            loggedInUser = db.authenticateUser(username, password);
            if (loggedInUser!= null) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                //models.User loggedInUser = db.getUser(username);
                new frmHomePage(loggedInUser);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new frmLogin();
    }
}