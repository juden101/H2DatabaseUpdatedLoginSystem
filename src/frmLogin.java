import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frmLogin extends JFrame {
    private JPanel pnlContent;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public frmLogin() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(pnlContent);
        setSize(600, 800);
        pnlContent.setPreferredSize(new Dimension(600, 800)); // Set preferred size for the main panel
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

        // Assuming DBAccess is a class that handles database operations
        DBAccess db = DBAccess.getInstance();
        boolean isAuthenticated = db.authenticateUser(username, password);

        if (isAuthenticated) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new frmManageUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new frmLogin();
    }
}