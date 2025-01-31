import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class frmManageUsers extends JFrame {
    private JPanel pnlContent;
    private JTable tblUsers;
    private JScrollPane scrollPane;

    private final DBAccess db = DBAccess.getInstance();

    public frmManageUsers() {
        setTitle("Manage Users");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        setContentPane(pnlContent);
        setSize(600, 800);
        pnlContent.setPreferredSize(new Dimension(600, 800)); // Set preferred size for the main panel
        pack(); // Resize the frame to fit the preferred size of its components


        setVisible(true);
    }

    private void loadUserData() {
        // Fetch user data from the database
        List<User> users = db.getAllUsers();

        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Username", "First Name", "Last Name", "Email"});

        // Add user data to the table model
        for (User user : users) {
            tableModel.addRow(new Object[]{
                    user.getUserName(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail()
            });
        }

        // Bind the table model to the JTable
        tblUsers.setModel(tableModel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frmManageUsers().setVisible(true));
    }
}