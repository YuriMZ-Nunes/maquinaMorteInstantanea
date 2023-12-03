package model;
import java.io.IOException;

import javax.swing.SwingUtilities;

import controller.Computer;
import controller.Loader;
import view.*;

public class App {
    public static void main(String[] args) throws Exception {
        Computer computer = new Computer(1000);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfaceSwing interfaceSwing = new InterfaceSwing(computer);
                interfaceSwing.setVisible(true);
            }
        });
        
        /*
        try {
            Loader.loadProgram(computer, "lib/program.txt");
            Executor.executeProgram(computer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
