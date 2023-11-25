package controller;
public class Computer {
    public String[] memory;
    public int[] registers;
    public int r1 = 0;
    public int r2 = 0;


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

    public void setRegisterR1(int value){
        this.r1 = value;
    }

    public void setRegisterR2(int value){
        this.r2 = value;
    }

    public int getRegisterR1(){
        return r1;
    }

    public int getRegisterR2(){
        return r2;
    }


    
}
