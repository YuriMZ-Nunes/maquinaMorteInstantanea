package model;
import controller.Computer;

public class Executor {
    private static final int OP_CODE_MASK = 0xFF0000;
    private static final int OPERAND_MASK = 0x0000FF;
    // private static final int REG_MASK = 0x00FF00;

    public static void executeProgram(Computer computer){
        int pc = 0;
        boolean isRunning = true;

        while (isRunning) {
            int instruction = computer.readMemory(pc);

            int opCode = (instruction & OP_CODE_MASK) >> 16;
            int operand = instruction &  OPERAND_MASK;
            // int reg = (instruction & REG_MASK) >> 8;

            switch(opCode) {
                case 0x18:
                    computer.registers[0] += operand;
                    break;
                case 0x3C:
                    pc = operand - 1;
                    break;
                case 0x00:
                    System.out.println("Valor final: " + computer.registers[0]);
                    isRunning = false;
                    break;
                default:
                    isRunning = false;
                    System.out.println("Opcode não encontrado: " + opCode);
            }

            pc++;
        }
    }
}
