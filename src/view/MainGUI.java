package view;
import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    LeftUpper panelLeftUpper;
    RightUpper panelRightUpper;
    LeftLower panelLeftLower;
    RightLower panelRightLower;

    public MainGUI() {
        setTitle("Interface");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,2));

        GridBagConstraints gbc = new GridBagConstraints();

        panelLeftUpper = new LeftUpper();
            gbc.gridx = 0;
            gbc.gridy = 0;
            //LeftUpper.configure(panelLeftUpper);
            add(panelLeftUpper, gbc);

        panelRightUpper = new RightUpper();
            gbc.gridx = 1;
            gbc.gridy = 0;
            //RightUpper.configure(panelRightUpper);
            add(panelRightUpper, gbc);
        
        panelLeftLower = new LeftLower();
            gbc.gridx = 0;
            gbc.gridy = 1;
            //LeftLower.configure(panelLeftLower);
            add(panelLeftLower, gbc);

        panelRightLower = new RightLower();
            gbc.gridx = 1;
            gbc.gridy = 1;
            //RightLower.configure(panelRightLower);
            add(panelRightLower, gbc);              

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
