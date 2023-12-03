package old;
import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    public MainGUI() {
        setTitle("Interface");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,2));

        JPanel panel = new JPanel(new GridLayout(1,1));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;

            LeftUpper.configure(panel);
            add(panel, gbc);

            panel = new JPanel(new GridLayout(1,1));
                gbc.gridx = 1;
            
            RightUpper.configure(panel);
            add(panel, gbc);

            panel = new JPanel(new GridLayout(1,1));
                gbc.gridx = 0;
                gbc.gridy = 1;

            LeftLower.configure(panel);
            add(panel, gbc);

            panel = new JPanel(new GridLayout(1,1));
                gbc.gridx = 1;
            RightLower.configure(panel);
            add(panel, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI main = new MainGUI();
                main.setVisible(true);
            }
        });
    }
}
