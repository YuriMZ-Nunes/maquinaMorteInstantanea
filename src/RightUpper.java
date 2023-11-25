import javax.swing.*;
import java.awt.*;

public class RightUpper {
    public static void configurar(JPanel panel) {
        JPanel panelSuperiorDireito = new JPanel(new GridLayout(5, 1));
        JLabel[] labelsVariaveis = new JLabel[5];
        for (int i = 0; i < labelsVariaveis.length; i++) {
            labelsVariaveis[i] = new JLabel("Variável " + (i + 1));
            panelSuperiorDireito.add(labelsVariaveis[i]);
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        panel.add(panelSuperiorDireito, gbc);
    }
}
