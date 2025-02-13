package forms.staff;

import models.User;

import javax.swing.*;
import java.awt.*;

public class frmMenuAdmin extends JFrame{
    private JPanel pnlContent;
    private JLabel lblRestaurant;
    private JTable tableMenus;

    public frmMenuAdmin(User user) {

        if (!user.getRole().equalsIgnoreCase("staff")) {

            JOptionPane.showMessageDialog(null, "Access Denied: You are not an admin!", "Error", JOptionPane.ERROR_MESSAGE);

            return;

        }

        setTitle("Menu Admin");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(pnlContent);
        setSize(600, 800);
        pnlContent.setPreferredSize(new Dimension(600, 800));
        pack();

        setVisible(true);
    }
}
