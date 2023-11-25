package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LeftUpper {
    
    public static void configure(JPanel panel) {
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbl.rowHeights = new int[] { 50, 50 };
        gbl.columnWidths = new int[] { 50, 100 };
        panel.setBorder(BorderFactory.createTitledBorder("Arquivo"));
        panel.setLayout(gbl);

        JButton btnLoad = new JButton("Carregar");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(btnLoad, gbc);

        JButton btnExec = new JButton("Executar");
        btnExec.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(btnExec, gbc);

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
        panel.add(scrollPane, gbc);
        configureLoadAction(btnLoad, btnExec, textAreaInput);
        configureExecAction(btnExec, textAreaInput);
    }
    
    private static void configureLoadAction(JButton btnLoad, JButton btnExec, JTextArea textAreaInput){

        btnLoad.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int window = fileChooser.showOpenDialog(null);
                if (window == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    
                    try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
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
    }

    private static void configureExecAction(JButton btnExec, JTextArea textAreaInput){
        btnExec.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                for (String line : textAreaInput.getText().split("\n")) {
                    System.out.println(line);
                }
            }
        });
    }
}
