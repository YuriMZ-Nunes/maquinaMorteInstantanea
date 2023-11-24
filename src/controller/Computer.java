package controller;
public class Computer {
    public int[] memory;
    public int[] registers;

    public Computer(int memorySize){
        memory = new int[memorySize];
        registers  = new int[7];
    }

    public int readMemory(int address){
        if (address >= 0 && address < memory.length){
            return memory[address];
        } else {
            System.err.println("Erro: Endereço invalido: " + address);
            return 0;
        }
    }

    public void writeMemory(int address, int value){
        if (address >= 0 && address < memory.length)
            memory[address] = value;
        else 
            System.err.println("Erro: Endereço invalido: " + address);
    }

    
}
