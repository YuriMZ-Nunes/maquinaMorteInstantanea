package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import controller.*;
import model.*;

public class InterfaceSwing extends JFrame {

    public InterfaceSwing(Computer C) {
        Computer system = C;

        // Configurações da janela principal
        setTitle("Interface Swing");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2)); // Usando GridLayout com 2 linhas e 2 colunas





        // Painel superior esquerdo Carregar Arq/Executar
        JPanel panelSuperiorEsquerdo = new JPanel();
        panelSuperiorEsquerdo.setBorder(BorderFactory.createTitledBorder("Arquivo"));
        GridBagLayout gbl = new GridBagLayout();
        panelSuperiorEsquerdo.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbl.rowHeights = new int[] { 50, 50 };  gbl.columnWidths = new int[] { 50, 100 };
        JButton btnLoad = new JButton("Carregar");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelSuperiorEsquerdo.add(btnLoad, gbc);

        JButton btnExec = new JButton("Executar");
        btnExec.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSuperiorEsquerdo.add(btnExec, gbc);
        JTextArea textAreaInput = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(textAreaInput);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 10, 0, 0);
                gbc.gridheight = 2;
                gbc.gridwidth = 2;
                gbc.fill = GridBagConstraints.BOTH;
                textAreaInput.setLineWrap(true);
            scrollPane.setPreferredSize(new Dimension(50, 200));
        panelSuperiorEsquerdo.add(scrollPane, gbc);





        // Painel superior direito com o valor de 7 registradores
        JPanel panelSuperiorDireito = new JPanel(new GridLayout(8, 2));
        panelSuperiorDireito.setBorder(BorderFactory.createTitledBorder("Registradores"));

        JLabel[] registerLabels = new JLabel[8];
        JLabel[] registerValues = new JLabel[8];

        for (int i = 0; i < registerValues.length; i++)
            registerValues[i] = new JLabel("Valor");

        registerLabels[0] = new JLabel("A:");
        registerLabels[1] = new JLabel("X:");
        registerLabels[2] = new JLabel("L:");
        registerLabels[3] = new JLabel("B:");
        registerLabels[4] = new JLabel("S:");
        registerLabels[5] = new JLabel("T:");
        registerLabels[6] = new JLabel("PC:");
        registerLabels[7] = new JLabel("SW:");

        for (int i = 0; i < registerLabels.length; i++) {
            panelSuperiorDireito.add(registerLabels[i]);
            panelSuperiorDireito.add(registerValues[i]);
        }





        // Painel inferior esquerdo com campos de entrada e saída
        JPanel panelInferiorEsquerdo = new JPanel(new GridLayout(2, 2));
        panelInferiorEsquerdo.setBorder(BorderFactory.createTitledBorder("I/O"));

        JTextField textFieldInput = new JTextField();
        JTextField textFieldOutput = new JTextField();
        panelInferiorEsquerdo.add(new JLabel("Input:"));
        panelInferiorEsquerdo.add(textFieldInput);
        panelInferiorEsquerdo.add(new JLabel("Output:"));
        panelInferiorEsquerdo.add(textFieldOutput);




        
        // Painel inferior direito com um campo de texto para endereços de memória
        JPanel panelInferiorDireito = new JPanel();
        panelInferiorDireito.setBorder(BorderFactory.createTitledBorder("Memória"));

        DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Endereço");
            model.addColumn("Valor");

        /*
        for (int i = 0; i < 100; i++) 
            model.addRow(new Object[]{"0x00"+i, i+1024+""+i+128});
        */

        JTable table = new JTable(model);
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(100); 

        JScrollPane scroll = new JScrollPane(table);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        table.setPreferredScrollableViewportSize(new Dimension(200, 175));
        table.setEnabled(false);
        panelInferiorDireito.add(new JScrollPane(table));





        // Adicionando os painéis à janela principal
        add(panelSuperiorEsquerdo);
        add(panelSuperiorDireito);
        add(panelInferiorDireito);
        add(panelInferiorEsquerdo);


        // Ação do botão "Carregar"
        StringBuilder file = new StringBuilder();
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int window = fileChooser.showOpenDialog(null);
                if (window == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    file.append(selectedFile.getAbsolutePath());
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    
                    try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            //file.append(line+"\n");
                            textAreaInput.append(line + "\n");
                            btnExec.setEnabled(true);
                        }
                    } 
                    catch (IOException exception) {
                        System.out.println("Error: " + exception.getMessage());
                    }
                }
            }
        });
                                


        btnExec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println(file.toString());
                String locationTxt = file.toString();
                try {
                    Loader.loadProgram(C, locationTxt);
                    Executor.executeProgram(C);
                    System.out.println("Loader carregado com sucesso!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                StringBuilder update = new StringBuilder();
                StringBuilder address = new StringBuilder();

                // Atualiza a GUI
                Map<String, Integer> memoryTable = new LinkedHashMap<>();
                memoryTable = Computer.getMemory();
                updateTable(model,memoryTable);

                Map<String, Integer> registers = new LinkedHashMap<>();
                registers = Computer.getRegisters();
                updateRegisters(registerValues, registers);
            } 
        });
    }

    //Joga o hashMap pra tabela da GUI
    void updateTable(DefaultTableModel model,Map <String,Integer> update){
        for (Map.Entry<String,Integer> entry : update.entrySet()){
            Vector <Object> row = new Vector<>();
            row.add(entry.getKey());
            row.add(entry.getValue());
            model.addRow(row);
        }
    }
    void updateRegisters(JLabel [] registers,Map <String, Integer> values){
        int i = 0;
        for (Map.Entry<String,Integer> entry : values.entrySet()){
            registers[i].setText(entry.getValue().toString());
            i++;
        }
    }
/* 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Computer C = new Computer(1000); //TESTE
                InterfaceSwing interfaceSwing = new InterfaceSwing(C);
                interfaceSwing.setVisible(true);
            }
        });
    }
*/
}






