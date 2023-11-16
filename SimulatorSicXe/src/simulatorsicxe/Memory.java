/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulatorsicxe;

/**
 *
 * @author abaca
 */
public class Memory {
    private int[] memory;
    
    public Memory(int size){
        memory = new int[size];
    }
    
    public int readMemory(int address){
        return memory[address];
    }
    
    public void writeMemory(int address, int value){
        memory[address] = value;
    }
}
