package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RightLower {

    public static void configure(JPanel panel) {
        panel.setBorder(BorderFactory.createTitledBorder("Memória"));

        DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Endereço");
            model.addColumn("Valor");

        for (int i = 0; i < 100; i++) 
            model.addRow(new Object[]{"0x00"+i, i+1024+""+i+128});

        JTable table = new JTable(model);
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(100); 

        JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        table.setPreferredScrollableViewportSize(new Dimension(200, 175));
        panel.add(scrollPane);
    }
}
