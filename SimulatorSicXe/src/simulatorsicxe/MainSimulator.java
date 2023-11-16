/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulatorsicxe;

/**
 *
 * @author abaca
 */
public class MainSimulator {
    public static void main(String[] args) {
        Registers registers = new Registers();
        Memory memory = new Memory(1000); // Por exemplo, 1000 posições de memória
        ControlUnit controlUnit = new ControlUnit();
        //pre-carga nas posictions 0 e 1 da memoria
        memory.writeMemory(0, 10);
        memory.writeMemory(1, 20);
        // Exemplo de execução de uma instrução (simulação)
        int opcode = 0; // Supondo um opcode de adição
        controlUnit.executeInstruction(opcode, registers, memory);
        System.out.println("Registrador 0: "+ registers.getRegisters(0));
    }
}
