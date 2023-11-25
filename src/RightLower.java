import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RightLower {

    public static void configure(JPanel panel) {
        panel.setBorder(BorderFactory.createTitledBorder("Memória"));
        // Criação do modelo de tabela
        DefaultTableModel modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Endereço");
        modeloTabela.addColumn("Valor");

        // Adicionando Exemplo de tabela preenchida
        for (int i = 0; i < 100; i++) 
            modeloTabela.addRow(new Object[]{"0x00"+i, i+1024+""+i+128});
        
        // Criação da tabela com o modelo
        JTable tabela = new JTable(modeloTabela);

        // Ajustando a largura da tabela
        tabela.getColumnModel().getColumn(0).setPreferredWidth(100); // Largura do Endereço de Memória
        tabela.getColumnModel().getColumn(1).setPreferredWidth(100); // Largura do Valor

        // Adicionando a tabela a um JScrollPane com barra de rolagem vertical
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Definindo o tamanho preferido da tabela para evitar que ela se expanda demais
        tabela.setPreferredScrollableViewportSize(new Dimension(200, 175));

        // Adicionando o JScrollPane ao painel fornecido
        panel.add(scrollPane);
    }
}
