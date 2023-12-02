package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceSwing extends JFrame {

    public InterfaceSwing() {
        // Configurações da janela principal
        setTitle("Interface Swing");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2)); // Usando GridLayout com 2 linhas e 2 colunas

        // Painel superior esquerdo com um botão "Carregar"
        JPanel panelSuperiorEsquerdo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCarregar = new JButton("Carregar");
        panelSuperiorEsquerdo.add(btnCarregar);

        // Painel superior direito com o valor de 5 variáveis
        JPanel panelSuperiorDireito = new JPanel(new GridLayout(5, 2));
        JLabel[] registerLabels = new JLabel[5];
        JLabel[] registerValues = new JLabel[5];

        for (int i = 0; i < registerValues.length; i++)
            registerValues[i] = new JLabel("Valor");

        registerLabels[0] = new JLabel("A:");
        registerLabels[1] = new JLabel("X:");
        registerLabels[2] = new JLabel("L:");
        registerLabels[3] = new JLabel("PC:");
        registerLabels[4] = new JLabel("SW:");

        for (int i = 0; i < registerLabels.length; i++) {
            panelSuperiorDireito.add(registerLabels[i]);
            panelSuperiorDireito.add(registerValues[i]);
        }
        
        // Painel inferior esquerdo com um campo de texto para endereços de memória
        JPanel panelInferiorEsquerdo = new JPanel();
        JTextArea textAreaEnderecos = new JTextArea(10, 20);
        panelInferiorEsquerdo.add(new JScrollPane(textAreaEnderecos));

        // Painel inferior direito com campos de entrada e saída
        JPanel panelInferiorDireito = new JPanel(new GridLayout(2, 2));
        JTextField textFieldInput = new JTextField();
        JTextField textFieldOutput = new JTextField();
        panelInferiorDireito.add(new JLabel("Input:"));
        panelInferiorDireito.add(textFieldInput);
        panelInferiorDireito.add(new JLabel("Output:"));
        panelInferiorDireito.add(textFieldOutput);

        // Adicionando os painéis à janela principal
        add(panelSuperiorEsquerdo);
        add(panelSuperiorDireito);
        add(panelInferiorEsquerdo);
        add(panelInferiorDireito);

        // Ação do botão "Carregar"
        btnCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Coloque aqui a lógica para carregar algo
                JOptionPane.showMessageDialog(InterfaceSwing.this, "Botão Carregar pressionado!");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfaceSwing interfaceSwing = new InterfaceSwing();
                interfaceSwing.setVisible(true);
            }
        });
    }
}