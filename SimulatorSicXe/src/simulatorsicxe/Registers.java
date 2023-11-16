/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulatorsicxe;

/**
 *
 * @author abaca
 */
public class Registers {
    private int[] registers;
    
    public Registers(){
        registers = new int[10]; //define 10 registradores
    }

    public int getRegisters(int regNum) {
        return registers[regNum];
    }
    
    public void setRegisters(int regNum, int value){
        registers[regNum] = value;
    }
}
