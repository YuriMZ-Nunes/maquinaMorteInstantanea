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

            int finalAregisterValue = computer.readRegister("A");
            int finalXregisterValue = computer.readRegister("X");
            int finalLregisterValue = computer.readRegister("L");
            int finalBregisterValue = computer.readRegister("B");
            int finalSregisterValue = computer.readRegister("S");
            int finalTregisterValue = computer.readRegister("T");
            int finalPCregisterValue = computer.readRegister("PC");
            int finalSWregisterValue = computer.readRegister("SW");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
