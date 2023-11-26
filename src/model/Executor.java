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
        computer.writeRegister("PC", pc);
        boolean isRunning = true;
        
        while (isRunning) {
            pc = computer.readRegister("PC");
            String instruction = computer.readInstructionsMemory(pc);
            int instructionSize = getInstructionSize(instruction);
            
            switch (instructionSize) {
                case 16:
                    runInstructionFormat2(computer, instruction);
                    break;
                case 24:
                    runInstructionFormat3And4(computer, instruction);
                    break;
                case 32:
                    runInstructionFormat3And4(computer, instruction);
                    break;
                default:
                    System.out.println("Formato da seguinte instrução invalido: " + instruction);
                    break;
            }

            pc++;
            computer.writeRegister("PC", pc);
        }
    }

    public static void runInstructionFormat2(Computer computer, String instruction){
        int instructionToInt = Integer.parseInt(instruction, 16);
        int opCode = (instructionToInt & OPCODE_8BITS_MASK) >> 8;
        int r1 = (instructionToInt & R1_MASK) >> 4;
        int r2 = instructionToInt & R2_MASK;

        String r1Key = getRegisterKey(r1);        
        String r2Key = getRegisterKey(r2);

        
        switch (opCode) {
            case 0x90: // ADDR
                int addR = computer.readRegister(r1Key) + computer.readRegister(r2Key);
                computer.writeRegister("R2", addR);
                break;
            case 0x04: // CLEAR
                computer.writeRegister(r1Key, 0);
                break;
            case 0xA0: // COMPR
                if(computer.readRegister(r1Key) > computer.readRegister(r2Key))
                    computer.writeRegister(r2Key, 0x3E);
                else if(computer.readRegister(r1Key) < computer.readRegister(r2Key))
                    computer.writeRegister(r2Key, 0x3C);
                else
                    computer.writeRegister(r2Key, 0x3D);
                break;
            case 0x9C: // DIVR
                int divR = computer.readRegister(r2Key) / computer.readRegister(r1Key);
                computer.writeRegister("R2", divR);
                break;
            case 0x98: // MULTR
                int multR = computer.readRegister(r1Key) * computer.readRegister(r2Key);
                computer.writeRegister("R2", multR);
                break;
            case 0xAC: // RMO
                int rmo = computer.readRegister(r1Key);
                computer.writeRegister(r2Key, rmo);
                break;
            case 0xA4: // SHIFTL
                int shiftL = computer.readRegister(r1Key) << r2;
                computer.writeRegister(r1Key, shiftL);
                break;
            case 0xA8: // SHIFTR
                int shiftR = computer.readRegister(r1Key) >> r2;
                computer.writeRegister(r1Key, shiftR);
                break;
            case 0x94: // SUBR
                int subR = computer.readRegister(r2Key) - computer.readRegister(r1Key);
                computer.writeRegister("R2", subR);
                break;
            case 0xB8: // TIXR
                if(computer.readRegister(r1Key) > computer.readRegister("X"))
                    computer.writeRegister("SW", 0x3C);
                else if(computer.readRegister(r1Key) < computer.readRegister("X"))
                    computer.writeRegister("SW", 0x3E);
                else
                    computer.writeRegister("SW", 0x3D);
                // incrementa 1 em X
                computer.writeRegister("X", computer.readRegister("X") + 1);
                break;
            default:
                System.out.println("OpCode não encontrado: " + String.format("0x%03X", opCode));
                break;
        }
    }

    public static void runInstructionFormat3And4(Computer computer, String instruction){
        int instructionToInt = Integer.parseInt(instruction, 16);
        int n, i, x, b, p, e, opCode, finalBits;
        String finalAddress;
        int value, finalValue;
        if(getInstructionSize(instruction) == 24){
            opCode = (instructionToInt & OPCODE_MASK) >> 18;
            n = (instructionToInt & N_MASK_24) >> 17;
            i = (instructionToInt & I_MASK_24) >> 16;
            x = (instructionToInt & X_MASK_24) >> 15;
            b = (instructionToInt & B_MASK_24) >> 14;
            p = (instructionToInt & P_MASK_24) >> 13;
            e = (instructionToInt & E_MASK_24) >> 12;
            finalBits = instructionToInt & DISP_MASK; // DISP
        } else {
            opCode = (instructionToInt & OPCODE_MASK) >> 26;
            n = (instructionToInt & N_MASK_32) >> 25;
            i = (instructionToInt & I_MASK_32) >> 24;
            x = (instructionToInt & X_MASK_32) >> 23;
            b = (instructionToInt & B_MASK_32) >> 22;
            p = (instructionToInt & P_MASK_32) >> 21;
            e = (instructionToInt & E_MASK_32) >> 20;
            finalBits = instructionToInt & ADDRESS_MASK;
        }

        String flags = "" + n + i + x + b + p + e;

        if(computer.verifyOpcodeSize6(opCode))
            opCode = opCode << 2;

        switch (opCode) {
            case 0x18: // ADD
                if(n != 0 && i != 1){
                    finalAddress = calcAddress(computer, flags, finalBits);
                    value = computer.readMemory(finalAddress);
                    finalValue = computer.readRegister("A") + value;
                    computer.writeRegister("A", finalValue);
                    break;
                }
                computer.writeRegister("A", finalBits);
                break;
            case 0x40: // AND
                if(n != 0 && i != 1){
                    finalAddress = calcAddress(computer, flags, finalBits);
                    value = computer.readMemory(finalAddress);
                    finalValue = value & computer.readRegister("A");
                    computer.writeRegister("A", finalValue);
                }
                computer.writeRegister("A", finalBits);
                break;
            case 0x28: // COMP
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                if(value > computer.readRegister("A"))
                    computer.writeRegister("SW", 0x3C);
                else if(value < computer.readRegister("A"))
                    computer.writeRegister("SW", 0x3E);
                else
                    computer.writeRegister("SW", 0x3D);

                break;
            case 0x24: // DIV
                break; 
            case 0x3C: // J
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                if(n != 0 && i != 1)
                    value = finalBits;
                computer.writeRegister("PC", value);
                break;
            case 0x30: // JEQ
                if(computer.readRegister("SW") == 0x3D){
                    finalAddress = calcAddress(computer, flags, finalBits);
                    value = computer.readMemory(finalAddress);
                    if(n != 0 && i != 1)
                        value = finalBits;
                    computer.writeRegister("PC", value);
                }
                break;
            case 0x34: // JGT
                if(computer.readRegister("SW") == 0x3E){
                    finalAddress = calcAddress(computer, flags, finalBits);
                    value = computer.readMemory(finalAddress);
                    if(n != 0 && i != 1)
                        value = finalBits;
                    computer.writeRegister("PC", value);
                }
                break;
            case 0x38: // JLT
                if(computer.readRegister("SW") == 0x3C){
                        finalAddress = calcAddress(computer, flags, finalBits);
                        value = computer.readMemory(finalAddress);
                        if(n != 0 && i != 1)
                            value = finalBits;
                        computer.writeRegister("PC", value);
                    }
                break;
            case 0x48: // JSUB
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                if(n != 0 && i != 1)
                    value = finalBits;
                computer.writeRegister("L", computer.readRegister("PC"));
                computer.writeRegister("PC", value);

                break;
            case 0x00: // LDA
                break;
            case 0x68: // LDB
                break;
            case 0x50: // LDCH
                break;
            case 0x08: // LDL
                break;
            case 0x6C: // LDS
                break;
            case 0x74: // LDT
                break;
            case 0x04: // LDX
                break;
            case 0x20: // MUL
                break;
            case 0x44: // OR
                if(n != 0 && i != 1){
                    finalAddress = calcAddress(computer, flags, finalBits);
                    value = computer.readMemory(finalAddress);
                    finalValue = value | computer.readRegister("A");
                    computer.writeRegister("A", finalValue);
                    break;
                }
                computer.writeRegister("A", finalBits);
                break;
            case 0x4C: // RSUB
                computer.writeRegister("PC", computer.readRegister("L"));
                break;
            case 0x0C: // STA
                break;
            case 0x78: // STB
                break;
            case 0x54: // STCH
                break;
            case 0x14: // STL
                break;
            case 0x7C: // STS
                break;
            case 0x84: // STT
                break;
            case 0x10: // STX
                break;
            case 0x1C: // SUB
                if(n != 0 && i != 1){
                    finalAddress = calcAddress(computer, flags, finalBits);
                    value = computer.readMemory(finalAddress);
                    finalValue = computer.readRegister("A") - value;
                    computer.writeRegister("A", finalValue);
                    break;
                }
                computer.writeRegister("A", finalBits);
                break;
            case 0x2C: // TIX
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);

                if(n != 0 && i != 1)
                    value = finalBits;
                
                if(value > computer.readRegister("X"))
                    computer.writeRegister("SW", 0x3C);
                else if(value < computer.readRegister("X"))
                    computer.writeRegister("SW", 0x3E);
                else
                    computer.writeRegister("SW", 0x3D);
                // incrementa 1 em X
                computer.writeRegister("X", computer.readRegister("X") + 1);
                break;
            default:
                System.out.println("OpCode não encontrado: " + String.format("0x%03X", opCode));
                break;
        }

    }
    

    public static String calcAddress(Computer computer, String flags, int lastBits){
        String finalAddress;
        String midAddress;
        int calc;
        switch (flags) {
            // Endereçamento direto
            case "110000":
                finalAddress = String.format("0x%03X", lastBits);
                return finalAddress;
            case "110001":
                finalAddress = String.format("0x%03X", lastBits);
                return finalAddress;
            case "110010":
                calc = computer.readRegister("PC") + lastBits;
                finalAddress = String.format("0x%03X", calc);
                return finalAddress;
            case "110100":
                calc = computer.readRegister("B") + lastBits;
                finalAddress = String.format("0x%03X", calc);
                return finalAddress;
            case "111000":
                calc = computer.readRegister("X") + lastBits;
                finalAddress = String.format("0x%03X", calc);
                return finalAddress;
            case "111001":
                calc = computer.readRegister("X") + lastBits;
                finalAddress = String.format("0x%03X", calc);
                return finalAddress;
            case "111010":
                calc = computer.readRegister("PC") + lastBits + computer.readRegister("X");
                finalAddress = String.format("0x%03X", calc);
                return finalAddress;
            case "111100":
                calc = computer.readRegister("B") + lastBits + computer.readRegister("X");
                finalAddress = String.format("0x%03X", calc);
                return finalAddress;
            // Endereçamento indireto
            case "100000":
                midAddress = String.format("0x%03X", lastBits);
                finalAddress = String.format("0x%03X", computer.readMemory(midAddress));
                return finalAddress;
            case "100001":
                midAddress = String.format("0x%03X", lastBits);
                finalAddress = String.format("0x%03X", computer.readMemory(midAddress));
                return finalAddress;
            case "100010":
                calc = computer.readRegister("PC") + lastBits;
                midAddress = String.format("0x%03X", calc);
                finalAddress = String.format("0x%03X", computer.readMemory(midAddress));
                return finalAddress;
            case "100100":
                calc = computer.readRegister("B") + lastBits;
                midAddress = String.format("0x%03X", calc);
                finalAddress = String.format("0x%03X", computer.readMemory(midAddress));
            // Endereçamento imediato
            case "010000":
                return String.format("0x%03X", lastBits);
            case "010001":
                return String.format("0x%03X", lastBits);
            case "010010":
                calc = computer.readRegister("PC") + lastBits;
                return String.format("0x%03X", lastBits);
            case "010100":
                calc = computer.readRegister("PC") + lastBits;
                return String.format("0x%03X", lastBits);
            default:
                System.out.println("Flags não encontradas: " + flags);
                break;
        }
        return "-100";
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

    public static String getRegisterKey(int register){
        switch (register) {
            case 0:
                return "A";
            case 1: 
                return "X";
            case 2:
                return "L";
            case 3:
                return "B";
            case 4:
                return "S";
            case 5:
                return "T";
            case 8:
                return "PC";
            case 9:
                return "SW";
            default:
                System.out.println("Registrador não encontrado: " + register);
                return "-100";
        }
    }

}

