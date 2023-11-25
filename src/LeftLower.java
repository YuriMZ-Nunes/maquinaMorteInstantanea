import javax.swing.*;


public class LeftLower {
    public static void configure(JPanel panel) {
        panel.setBorder(BorderFactory.createTitledBorder("Output"));
        JTextArea textAreaOutput = new JTextArea();
            textAreaOutput.setLineWrap(true);
            textAreaOutput.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textAreaOutput);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollPane);
    }
}
