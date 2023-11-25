import javax.swing.*;
import java.awt.*;

public class LeftLower {
    public static void configurar(JPanel panel) {
        JTextArea textAreaEnderecos = new JTextArea(10, 20);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        panel.add(new JScrollPane(textAreaEnderecos), gbc);
    }
}
