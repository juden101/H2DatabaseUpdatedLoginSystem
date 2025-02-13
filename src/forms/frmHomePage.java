package forms;

import models.User;

import javax.swing.*;
import java.awt.*;

public class frmHomePage extends JFrame {
    private JPanel pnlContent;
    private JLabel lblWelcome;
    private JLabel lblLogo;
    private JLabel lblRole;

    public frmHomePage(User user) {
        setTitle("Home Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(pnlContent);
        setSize(600, 600);
        pnlContent.setPreferredSize(new Dimension(600, 600)); // Set preferred size for the main panel
        pack(); // Resize the frame to fit the preferred size of its components

        setLocationRelativeTo(null);


        lblWelcome.setText("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        lblRole.setText("Role: " + user.getRole());
        setVisible(true);
    }
}
