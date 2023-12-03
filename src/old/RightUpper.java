package old;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;


public class RightUpper {

    public static void configure(JPanel panel) {
        panel.setBorder(BorderFactory.createTitledBorder("Registradores"));
        panel.setLayout(new GridLayout(5, 2, 5, 5)); 
        Map<String, String> registradores = new HashMap<>(Map.of("A", "12", "X", "6", "L", "0XC001", "PC", "22", "SW", "0xc001F"));

        registradores.entrySet().forEach(entry -> {
            JLabel reg_key = new JLabel(entry.getKey() + " :");
                reg_key.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            panel.add(reg_key);

            JLabel reg_value = new JLabel(entry.getValue());
                reg_value.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            panel.add(reg_value);
        });
    }
}