import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    public MainGUI() {
        setTitle("Interface GridBag Principal");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Adiciona as partes configuradas ao layout principal
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        LeftUpper.configurar(panel);
        add(panel, gbc);

        panel = new JPanel(new GridBagLayout());
        gbc.gridx = 1;
        RightUpper.configurar(panel);
        add(panel, gbc);

        panel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 1;
        LeftLower.configurar(panel);
        add(panel, gbc);

        panel = new JPanel(new GridBagLayout());
        gbc.gridx = 1;
        LeftLower.configurar(panel);
        add(panel, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI classePrincipal = new MainGUI();
                classePrincipal.setVisible(true);
            }
        });
    }
}
