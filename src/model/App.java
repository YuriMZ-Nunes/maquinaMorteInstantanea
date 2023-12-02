package model;
import java.io.IOException;

import controller.Computer;
import controller.Loader;

public class App {
    public static void main(String[] args) throws Exception {
        Computer computer = new Computer(1000);

        try {
            Loader.loadProgram(computer, "lib/program.txt");
            Executor.executeProgram(computer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
