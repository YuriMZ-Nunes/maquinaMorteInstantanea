package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Loader {
    public static void loadProgram(Computer computer, String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int address = 0;

        while((line = reader.readLine()) != null){
            int instruction = Integer.parseInt(line, 16);
            computer.writeMemory(address, instruction);
            address++;
        }

        reader.close();
    }
}
