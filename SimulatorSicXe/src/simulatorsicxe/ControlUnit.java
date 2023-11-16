/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulatorsicxe;

/**
 *
 * @author abaca
 */
public class ControlUnit {
    public void executeInstruction(int opcode,Registers registers, Memory memory){
        switch (opcode){
            case 0:
                //implementação da adição
                int regA = memory.readMemory(0);
                int regB = memory.readMemory(1);
                int result = regA + regB;
                registers.setRegisters(0,result);
                break;
            case 1:
                //implementação da subtração
                int regX = memory.readMemory(0);
                int regY = memory.readMemory(1);
                int subresult = regX + regY;
                registers.setRegisters(0,subresult);
                break;
            default:
                //tratar instrução inválida
                System.out.println("Invalid Instructon");
                break;
        }
    }
}
