import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frmHome2 extends JFrame {
    private JPanel pnlContent;
    private JLabel logo;
    private JButton loginButton;
    private JButton registerButton;

    public frmHome2() {
        setTitle("Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pnlContent);
        pack();
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new frmLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new frmRegister();
            }
        });
    }

    private void createUIComponents() {
        // Custom component creation code
        logo = new JLabel(new ImageIcon("src/images/logo.png"));
    }

    public static void main(String[] args) {
        new frmHome2();
    }
}