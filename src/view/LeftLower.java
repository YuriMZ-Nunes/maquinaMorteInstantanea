package view;
import javax.swing.*;
import java.awt.*;

public class LeftLower extends JPanel{
 
    JTextArea textAreaOutput;

    public LeftLower()
    {
        setLayout(new GridLayout(1,1));

        setBorder(BorderFactory.createTitledBorder("Output"));

        textAreaOutput = new JTextArea();
            textAreaOutput.setLineWrap(true);
            textAreaOutput.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textAreaOutput);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
    }
}
