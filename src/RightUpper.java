import javax.swing.*;
import java.awt.*;

public class RightUpper {

    public static void configure(JPanel panel) {
        panel.setBorder(BorderFactory.createTitledBorder("Registradores"));
        panel.setLayout(new GridLayout(5, 2, 5, 5)); // Layout com 6 linhas, 2 colunas e espaçamento

        // Adicionando rótulos para os nomes dos registradores
        JLabel labelRegistrador1 = new JLabel("            A :");
        JLabel labelRegistrador2 = new JLabel("            X :");
        JLabel labelRegistrador3 = new JLabel("            L :");
        JLabel labelRegistrador4 = new JLabel("            PC :");
        JLabel labelRegistrador5 = new JLabel("            SW :");

        // Adicionando caixas de texto para exibir os valores dos registradores
        JTextField reg_A_value = new JTextField();
        JTextField reg_X_value = new JTextField();
        JTextField reg_L_value = new JTextField();
        JTextField reg_PC_value = new JTextField();
        JTextField reg_SW_value = new JTextField();

        // Configurando caixas de texto como não editáveis
        reg_A_value.setEditable(false);
        reg_X_value.setEditable(false);
        reg_L_value.setEditable(false);
        reg_PC_value.setEditable(false);
        reg_SW_value.setEditable(false);

        //Adicionando Exemplos
        reg_A_value.setText("12");
        reg_X_value.setText("6");
        reg_L_value.setText("0XC001");
        reg_PC_value.setText("22");
        reg_SW_value.setText("0xc001F");

        // Adicionando rótulos e caixas de texto ao painel
        panel.add(labelRegistrador1);
        panel.add(reg_A_value);
        panel.add(labelRegistrador2);
        panel.add(reg_X_value);
        panel.add(labelRegistrador3);
        panel.add(reg_L_value);
        panel.add(labelRegistrador4);
        panel.add(reg_PC_value);
        panel.add(labelRegistrador5);
        panel.add(reg_SW_value);
    }
}