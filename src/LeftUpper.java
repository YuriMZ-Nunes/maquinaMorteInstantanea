import javax.swing.*;
import java.awt.*;

public class LeftUpper {
    public static void configurar(JPanel panel) {
        JButton btnCarregar = new JButton("Carregar");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(btnCarregar, gbc);
    }
}
