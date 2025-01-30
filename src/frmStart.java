import javax.swing.*;
import java.awt.*;

public class frmStart extends JFrame {
    private JPanel pnlContent;

    public frmStart() {
        pnlContent = new JPanel(new BorderLayout()); // Initialize pnlContent with a layout manager
        setTitle("Start Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pnlContent);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new frmStart();
            }
        });
    }
}