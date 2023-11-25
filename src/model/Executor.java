package model;
import controller.Computer;

public class Executor{
    // mascaras para as flags e disp e address
    static int OPCODE_MASK = 0xFC0000;

    static int N_MASK_24 = 0b000000100000000000000000;
    static int N_MASK_32 = 0b00000010000000000000000000000000;      
     
    static int I_MASK_24 = 0b000000010000000000000000;
    static int I_MASK_32 = 0b00000001000000000000000000000000;
   
    static int X_MASK_24 = 0b000000001000000000000000;
    static int X_MASK_32 = 0b00000000100000000000000000000000;

    static int B_MASK_24 = 0b000000000100000000000000;
    static int B_MASK_32 = 0b00000000010000000000000000000000;

    static int P_MASK_24 = 0b000000000010000000000000;
    static int P_MASK_32 = 0b00000000001000000000000000000000;

    static int E_MASK_24 = 0b000000000001000000000000;
    static int E_MASK_32 = 0b00000000000100000000000000000000;

    static int DISP_MASK = 0x00FFFF;
    static int ADDRESS_MASK = 0xFFFF;

    //mascaras para instruções do formato 1 e 2
    static int OPCODE_8BITS_MASK = 0xFF00;
    static int R1_MASK = 0x00F0;
    static int R2_MASK = 0x000F;

    public static void executeProgram(Computer computer){
        
        int pc = 0;
        computer.writeRegister("SW", pc);
        boolean isRunning = true;
        
        while (isRunning) {
            pc = computer.readRegister("SW");
            String instruction = computer.readInstructionsMemory(pc);
            int instructionSize = getInstructionSize(instruction);
            
            switch (instructionSize) {
                case 8:
                    runInstructionFormat1(computer, instruction);
                    break;
                case 12:
                    runInstructionFormat2(computer, instruction);
                    break;
                case 24:
                    runInstructionFormat3(computer, instruction);
                    break;
                case 32:
                    runInstructionFormat4(computer, instruction);
                    break;
                default:
                    System.out.println("Formato da seguinte instrução invalido: " + instruction);
                    break;
            }

            pc++;
            computer.writeRegister("SW", pc);
        }
    }

    public static void runInstructionFormat1(Computer computer, String instruction){
        int instructionToInt = Integer.parseInt(instruction, 16);
        int opCode = (instructionToInt & OPCODE_8BITS_MASK) >> 8;

        switch (opCode) {
            case 0x18:
                
                break;
            default:
                break;
        }
    }

    public static void runInstructionFormat2(Computer computer, String instruction){
        int instructionToInt = Integer.parseInt(instruction, 16);
        int opCode = (instructionToInt & OPCODE_8BITS_MASK) >> 8;
        int r1 = (instructionToInt & R1_MASK) >> 4;
        int r2 = instructionToInt & R1_MASK;

        switch (opCode) {
            case 0x18:
                
                break;
            default:
                break;
        }

    }

    public static void runInstructionFormat3(Computer computer, String instruction){
        int instructionToInt = Integer.parseInt(instruction, 16);
        int opCode = (instructionToInt & OPCODE_MASK) >> 18;
        int n = (instructionToInt & N_MASK_24) >> 17;
        int i = (instructionToInt & I_MASK_24) >> 16;
        int x = (instructionToInt & X_MASK_24) >> 15;
        int b = (instructionToInt & B_MASK_24) >> 14;
        int p = (instructionToInt & P_MASK_24) >> 13;
        int e = (instructionToInt & E_MASK_24) >> 12;
        int disp = instructionToInt & DISP_MASK;

        String flags = "" + n + i + x + b + p + e;

        switch (opCode) {
            case 0x18:
                computer.writeRegister("A", disp);
                int finalAddress = calcAddress(computer, flags, disp);
                break;
            default:
                break;
        }

    }
    
    public static void runInstructionFormat4(Computer computer, String instruction){
        int instructionToInt = Integer.parseInt(instruction, 16);
        int opCode = (instructionToInt & OPCODE_MASK) >> 26;
        int n = (instructionToInt & N_MASK_32) >> 25;
        int i = (instructionToInt & I_MASK_32) >> 24;
        int x = (instructionToInt & X_MASK_32) >> 23;
        int b = (instructionToInt & B_MASK_32) >> 22;
        int p = (instructionToInt & P_MASK_32) >> 21;
        int e = (instructionToInt & E_MASK_32) >> 20;
        int address = instructionToInt & ADDRESS_MASK;
        String flags = "" + n + i + x + b + p + e;

        switch (opCode) {
            case 0xAA:
                int finalAddress = calcAddress(computer, flags, address);

                break;
            default:
                break;
        }


    }

    public static int calcAddress(Computer computer, String flags, int lastBits){
        switch (flags) {
            // Endereçamento direto
            case "110000":
                return lastBits;
            case "110001":
                return lastBits;
            case "110010":
                return computer.readRegister("PC") + lastBits;
            case "110100":
                return computer.readRegister("B") + lastBits;
            case "111000":
                return computer.readRegister("X") + lastBits;
            case "111001":
                return computer.readRegister("X") + lastBits;
            case "111010":
                return computer.readRegister("B") + lastBits + computer.readRegister("X");
            case "111100":
            // Endereçamento indireto
            case "100000":

            case "100001":

            case "100010":

            case "100100":
            // Endereçamento imediato
            case "010000":

            case "010001":

            case "010010":

            case "010100":

            default:
        }
        return 1;
    }

    public static int getInstructionSize(String instruction){
        StringBuilder binary = new StringBuilder();
        for(int i = 0; i < instruction.length(); i++){
            String binSegment = Integer.toBinaryString(Integer.parseInt(instruction.substring(i, i + 1), 16));
            binSegment = "0000" + binSegment;
            binary.append(binSegment.substring(binSegment.length() - 4));
        }
        
        return binary.toString().length();
    }
}