package view;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;


public class RightUpper extends JPanel {

    Map<String, String> registradores;
    JLabel reg_key;
    JLabel reg_value;

    public RightUpper(){
        setBorder(BorderFactory.createTitledBorder("Registradores"));
        setLayout(new GridLayout(5, 2, 5, 5)); 
        registradores = new HashMap<>(Map.of("A", "12", "X", "6", "L", "0XC001", "PC", "22", "SW", "0xc001F"));

        registradores.entrySet().forEach(entry -> {
            reg_key = new JLabel(entry.getKey() + " :");
                reg_key.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
                add(reg_key);

            reg_value = new JLabel(entry.getValue());
                reg_value.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
                add(reg_value);
        });
    }
}