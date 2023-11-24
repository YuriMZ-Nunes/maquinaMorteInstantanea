package controller;
public class Computer {
    public String[] memory;
    public int[] registers;

    public Computer(int memorySize){
        memory = new String[memorySize];
        registers  = new int[7];
    }

    public String readMemory(int address){
        if (address >= 0 && address < memory.length){
            return memory[address];
        } else {
            System.err.println("Erro: Endereço invalido: " + address);
            return "-100";
        }
    }

    public void writeMemory(int address, String value){
        if (address >= 0 && address < memory.length)
            memory[address] = value;
        else 
            System.err.println("Erro: Endereço invalido: " + address);
    }

    
}
