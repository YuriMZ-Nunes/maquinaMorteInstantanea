import javax.swing.*;
import java.awt.*;

public class RightLower {
    public static void configurar(JPanel panel) {
        JPanel panelInferiorDireito = new JPanel(new GridLayout(2, 2));
        JTextField textFieldInput = new JTextField();
        JTextField textFieldOutput = new JTextField();
        panelInferiorDireito.add(new JLabel("Input:"));
        panelInferiorDireito.add(textFieldInput);
        panelInferiorDireito.add(new JLabel("Output:"));
        panelInferiorDireito.add(textFieldOutput);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        panel.add(panelInferiorDireito, gbc);
    }
}
