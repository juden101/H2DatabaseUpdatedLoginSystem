import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class frmHome  extends JFrame{
    private JPanel contentPane;

    public frmHome() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }

    public static void main(String[] args) {
        new frmHome().setVisible(true);
    }

}
