package controller;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Computer {
    public static String[] instructionsMemory;
    public static List<Integer> opcodeListFormatSize6 = new ArrayList<Integer>();  
    public static Map<String, Integer> memory = new LinkedHashMap<>();
    public static Map<String, Integer> registers = new HashMap<>();

    public Computer(int memorySize){
        instructionsMemory = new String[1000];

        createMemory(memory);
        createRegisters(registers);
        fillOpcodeListSize6(opcodeListFormatSize6);

    }

    public int readMemory(String address){
        if(memory.containsKey(address))
            return memory.get(address);
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
        for(int i = 1; i <= 0xFFF; i++){
            String position = String.format("0x%03X", i);
            memory.put(position, 0);
        }
    }

    public void createRegisters(Map<String, Integer> registers){
        registers.put("A", 0);        
        registers.put("X", 0);
        registers.put("L", 0);
        registers.put("B", 0);
        registers.put("S", 0);
        registers.put("T", 0);
        registers.put("PC", 0);
        registers.put("SW", 0); 

    }

    public void fillOpcodeListSize6(List<Integer> opcodeList){
        opcodeList.add(0x18);
        opcodeList.add(0x40);
        opcodeList.add(0x28);
        opcodeList.add(0x24);
        opcodeList.add(0x3C);
        opcodeList.add(0x30);
        opcodeList.add(0x34);
        opcodeList.add(0x38);
        opcodeList.add(0x48);
        opcodeList.add(0x00);
        opcodeList.add(0x68);
        opcodeList.add(0x50);
        opcodeList.add(0x08);
        opcodeList.add(0x6C);
        opcodeList.add(0x74);
        opcodeList.add(0x04);
        opcodeList.add(0x20);
        opcodeList.add(0x44);
        opcodeList.add(0x4C);
        opcodeList.add(0x0C);
        opcodeList.add(0x78);
        opcodeList.add(0x54);
        opcodeList.add(0x14);
        opcodeList.add(0x7C);
        opcodeList.add(0x84);
        opcodeList.add(0x10);
        opcodeList.add(0x1C);
        opcodeList.add(0x2C);
        opcodeList.add(0xB8);
    }


    public boolean verifyOpcodeSize6(int opCode){
        if(opcodeListFormatSize6.contains(opCode))
            return false;
        if(opcodeListFormatSize6.contains(opCode << 2))
            return true;
        return false;
    }

    public static Map<String, Integer> getMemory() {
        return memory;
    }
    
    public static Map<String, Integer> getRegisters() {
        return registers;
    }
}
