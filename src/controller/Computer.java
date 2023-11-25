package controller;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Computer {
    public static String[] instructionsMemory;
    public static Map<String, Integer> memory = new LinkedHashMap<>();
    public static Map<String, Integer> registers = new HashMap<>();

    public Computer(int memorySize){
        instructionsMemory = new String[1000];

        createMemory(memory);

        createRegisters(registers);

    }

    public int readMemory(String address){
        if(memory.containsKey(address)){
            return memory.get(address);
        }
        System.err.println("Erro: Endereço invalido: " + address);
        return -100;
        
    }

    public void writeMemory(String address, int value){
        if (memory.containsKey(address))
            memory.put(address, value);
        else 
            System.err.println("Erro: Endereço invalido: " + address);
    }

    public int readRegister(String registerString){
        if(registers.containsKey(registerString))
            return registers.get(registerString);
        System.err.println("Erro: Registrador invalido: " + registerString);
        return -100;
    }

    public void writeRegister(String registerString,int value) {
        if(registers.containsKey(registerString)){
            registers.put(registerString, value);
        }
    }

    public String readInstructionsMemory(int address){
        return instructionsMemory[address];
    }

    public void writeInstructionsMemory(int address, String line){
        instructionsMemory[address] = line;
    }

    public void createMemory(Map<String, Integer> memory){
        for(int i = 0; i <= 0xFFF; i++){
            String position = String.format("0x%03X", i);
            memory.put(position, 0);
        }
    }

    public void createRegisters(Map<String, Integer> registers){
        registers.put("A", 0);        
        registers.put("X", 0);
        registers.put("L", 0);
        registers.put("B", 0);
        registers.put("PC", 0);
        registers.put("SW", 0);    
        registers.put("R1", 0);
        registers.put("R2", 0);
    }


    
}
