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
    static boolean isRunning = true;

    //  responsável por executar o programa armazenado na memória da máquina
    public static void executeProgram(Computer computer){
        //  inicializa o contador com zero
        int pc = 0;
        computer.writeRegister("PC", pc);
        
        //  inicializa um laço que continuará executando enquanto a var isRunning for verdadeira
        while (isRunning) {
            //  lê o valor atual de PC 
            pc = computer.readRegister("PC");
            //  lê a instrução na posição indicada pelo PC
            String instruction = computer.readInstructionsMemory(pc);
            //  determina o tamanho da instrução lida
            int instructionSize = getInstructionSize(instruction);
            //  com base no tamanho da instrução, o codigo executa de forma diferente
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
            //  incrementa o contador de programa para apontar para próxima instrução
            pc++;
            //  escreve no mapa de registrador o incremento 
            computer.writeRegister("PC", pc);
        }
    }
    //  executa uma instrução do formato 2 na máquina
    //  recebe o computer e a instruction
    public static void runInstructionFormat2(Computer computer, String instruction){
        //  converte a String instruction para inteiro na base decimal
        int instructionToInt = Integer.parseInt(instruction, 16); //    o nº 16 indica que instruction está na base hexadecimal
        //  extração de Campos
        //  isola os 8 bits mais significativo para o opocode
        int opCode = (instructionToInt & OPCODE_8BITS_MASK) >> 8;
        //  isola os 4 bits intermediários para obter o registrador 1
        int r1 = (instructionToInt & R1_MASK) >> 4;
        //  isola os 4 bits menos significativos para obter o registrador 2
        int r2 = instructionToInt & R2_MASK;
        //  os nº são convertidos em chaves legíveis para acessar o mapa de registradores
        String r1Key = getRegisterKey(r1);        
        String r2Key = getRegisterKey(r2);

        //  Aqui ocorre as operações identificadas pelo opcodes
        switch (opCode) {
            case 0x90: // ADDR
                int addR = computer.readRegister(r1Key) + computer.readRegister(r2Key);
                computer.writeRegister(r2Key, addR);
                break;
            case 0x04: // CLEAR
                computer.writeRegister(r1Key, 0);
                break;
            case 0xA0: // COMPR
                if(computer.readRegister(r1Key) > computer.readRegister(r2Key))
                    computer.writeRegister("SW", 0x3E);
                else if(computer.readRegister(r1Key) < computer.readRegister(r2Key))
                    computer.writeRegister("SW", 0x3C);
                else
                    computer.writeRegister("SW", 0x3D);
                break;
            case 0x9C: // DIVR
                int divR = computer.readRegister(r2Key) / computer.readRegister(r1Key);
                computer.writeRegister(r2Key, divR);
                break;
            case 0x98: // MULTR
                int multR = computer.readRegister(r1Key) * computer.readRegister(r2Key);
                computer.writeRegister(r2Key, multR);
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
                computer.writeRegister(r2Key, subR);
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
            case 0x99:
                isRunning = false;
                break;
            default:
                System.out.println("OpCode não encontrado: " + String.format("0x%03X", opCode));
                break;
        }
    }

    public static void runInstructionFormat3And4(Computer computer, String instruction){
        //  converte a String instruction para inteiro na base decimal
        int instructionToInt = Integer.parseInt(instruction, 16);
        int n, i, x, b, p, e, opCode, finalBits;
        String finalAddress;
        int value, finalValue;
        //  determina o tamanho da instrução 24 ou 32
        if(getInstructionSize(instruction) == 24){
            //  se a instrução é 24 bits, os campos n, i, x, b, p, e, opCode, finalBits; 
            //  são extraídos usando operações bitwise ("&"e">>") 
            opCode = (instructionToInt & OPCODE_MASK) >> 18; // move os bits selecionados para a direita
            n = (instructionToInt & N_MASK_24) >> 17;
            i = (instructionToInt & I_MASK_24) >> 16;
            x = (instructionToInt & X_MASK_24) >> 15;
            b = (instructionToInt & B_MASK_24) >> 14;
            p = (instructionToInt & P_MASK_24) >> 13;
            e = (instructionToInt & E_MASK_24) >> 12;
            finalBits = instructionToInt & DISP_MASK; // DISP
            //  se tivermos um valor inteiro 5, que em binário é 101, e uma máscara de bits 3, 
            //  que em binário é 011, ao realizar a operação 5 & 3 (E lógico bit a bit), 
            //  o resultado será 1, que em binário é 001. Isso ocorre porque o & lógico compara cada 
            //  bit correspondente e retorna 1 somente se ambos os bits forem 1.
        } else {
            opCode = (instructionToInt & OPCODE_MASK) >> 26;
            n = (instructionToInt & N_MASK_32) >> 25;
            i = (instructionToInt & I_MASK_32) >> 24;
            x = (instructionToInt & X_MASK_32) >> 23;
            b = (instructionToInt & B_MASK_32) >> 22;
            p = (instructionToInt & P_MASK_32) >> 21;
            e = (instructionToInt & E_MASK_32) >> 20;
            finalBits = instructionToInt & ADDRESS_MASK; // address
        }

        String flags = "" + n + i + x + b + p + e;

        if(computer.verifyOpcodeSize6(opCode))
            opCode = opCode << 2;

        switch (opCode) {
            case 0x18: // ADD
                if(n == 0 && i == 1){
                    finalValue = computer.readRegister("A") + finalBits;
                    computer.writeRegister("A", finalValue);
                    break;
                }
                //  encontra o endereço 
                finalAddress = calcAddress(computer, flags, finalBits);
                // passa o conteudo do endereço para a var value
                value = computer.readMemory(finalAddress);
                //  soma o value com registrador A
                finalValue = computer.readRegister("A") + value;
                //  escreve no registrador A o finalvalue
                computer.writeRegister("A", finalValue);
                break;
            case 0x40: // AND
                if(n == 0 && i == 1){
                    computer.writeRegister("A", finalBits);
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                finalValue = value & computer.readRegister("A");
                computer.writeRegister("A", finalValue);
                break;
            case 0x28: // COMP
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                if(n == 0 && i == 1)
                    value = finalBits;
                if(value > computer.readRegister("A"))
                    computer.writeRegister("SW", 0x3C);
                else if(value < computer.readRegister("A"))
                    computer.writeRegister("SW", 0x3E);
                else
                    computer.writeRegister("SW", 0x3D);
                break;
            case 0x24: // DIV 
                if(n == 0 && i == 1){
                    finalValue = (int)(computer.readRegister("A") / finalBits); //    pega a parte inteira da divisão
                    computer.writeRegister("A", finalValue);
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                finalValue = (int)(computer.readRegister("A") / value); //    pega a parte inteira da divisão
                computer.writeRegister("A", finalValue);
                break; 
            case 0x3C: // J
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                if(n == 0 && i == 1)
                    value = finalBits;
                computer.writeRegister("PC", value);
                break;
            case 0x30: // JEQ
                if(computer.readRegister("SW") == 0x3D){
                    finalAddress = calcAddress(computer, flags, finalBits);
                    value = computer.readMemory(finalAddress);
                    if(n == 0 && i == 1)
                        value = finalBits;
                    computer.writeRegister("PC", value);
                }
                break;
            case 0x34: // JGT
                if(computer.readRegister("SW") == 0x3E){
                    finalAddress = calcAddress(computer, flags, finalBits);
                    value = computer.readMemory(finalAddress);
                    if(n == 0 && i == 1)
                        value = finalBits;
                    computer.writeRegister("PC", value);
                }
                break;
            case 0x38: // JLT
                if(computer.readRegister("SW") == 0x3C){
                        finalAddress = calcAddress(computer, flags, finalBits);
                        value = computer.readMemory(finalAddress);
                        if(n == 0 && i == 1)
                            value = finalBits;
                        computer.writeRegister("PC", value);
                    }
                break;
            case 0x48: // JSUB
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                if(n == 0 && i == 1)
                    value = finalBits;
                computer.writeRegister("L", computer.readRegister("PC"));
                computer.writeRegister("PC", value);

                break;
            case 0x00: // LDA
                if(n == 0 && i == 1){
                    computer.writeRegister("A", finalBits);
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                computer.writeRegister("A", value);
                break;
            case 0x68: // LDB
                if(n == 0 && i == 1){
                    computer.writeRegister("B", finalBits);
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                computer.writeRegister("B", value);
                break;
            case 0x50: // LDCH
                
                break;
            case 0x08: // LDL
                if(n == 0 && i == 1){
                    computer.writeRegister("L", finalBits);
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                computer.writeRegister("L", value);
                break;
            case 0x6C: // LDS
                if(n == 0 && i == 1){
                    computer.writeRegister("S", finalBits);
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                computer.writeRegister("S", value);
                break;
            case 0x74: // LDT
                if(n == 0 && i == 1){
                    computer.writeRegister("T", finalBits);
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                computer.writeRegister("T", value);
                break;
            case 0x04: // LDX
                if(n == 0 && i == 1){
                    computer.writeRegister("X", finalBits);
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                computer.writeRegister("X", value);
                break;
            case 0x20: // MUL
                if(n == 0 && i == 1){
                    finalValue = computer.readRegister("A") * finalBits;
                    computer.writeRegister("A", finalValue);
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                finalValue = computer.readRegister("A") * value;
                computer.writeRegister("A", finalValue);
                break;
            case 0x44: // OR
                if(n == 0 && i == 1){
                    computer.writeRegister("A", finalBits);
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                finalValue = value | computer.readRegister("A");
                computer.writeRegister("A", finalValue);
                break;
            case 0x4C: // RSUB
                computer.writeRegister("PC", computer.readRegister("L"));
                break;
            case 0x0C: // STA
                if(n == 0 && i == 1){
                    finalAddress = String.format("0x%03X", finalBits);
                    computer.writeMemory(finalAddress, computer.readRegister("A"));
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                computer.writeMemory(finalAddress, computer.readRegister("A"));
                break;
            case 0x78: // STB
                if(n == 0 && i == 1){
                    finalAddress = String.format("0x%03X", finalBits);
                    computer.writeMemory(finalAddress, computer.readRegister("B"));
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                computer.writeMemory(finalAddress, computer.readRegister("B"));
                break;
            case 0x54: // STCH

                break;
            case 0x14: // STL
                if(n == 0 && i == 1){
                    finalAddress = String.format("0x%03X", finalBits);
                    computer.writeMemory(finalAddress, computer.readRegister("L"));
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                computer.writeMemory(finalAddress, computer.readRegister("L"));
                break;
            case 0x7C: // STS
                if(n == 0 && i == 1){
                    finalAddress = String.format("0x%03X", finalBits);
                    computer.writeMemory(finalAddress, computer.readRegister("S"));
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                computer.writeMemory(finalAddress, computer.readRegister("S"));
                break;
            case 0x84: // STT
                if(n == 0 && i == 1){
                    finalAddress = String.format("0x%03X", finalBits);
                    computer.writeMemory(finalAddress, computer.readRegister("T"));
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                computer.writeMemory(finalAddress, computer.readRegister("T"));
                break;
            case 0x10: // STX
                if(n == 0 && i == 1){
                    finalAddress = String.format("0x%03X", finalBits);
                    computer.writeMemory(finalAddress, computer.readRegister("X"));
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                computer.writeMemory(finalAddress, computer.readRegister("X"));
                break;
            case 0x1C: // SUB
                if(n == 0 && i == 1){
                    value = computer.readRegister("A") - finalBits;
                    computer.writeRegister("A", value);
                    break;
                }
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);
                finalValue = computer.readRegister("A") - value;
                computer.writeRegister("A", finalValue);
                break;
            case 0x2C: // TIX
                finalAddress = calcAddress(computer, flags, finalBits);
                value = computer.readMemory(finalAddress);

                if(n == 0 && i == 1)
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
    //  calcula o endereço de memória com base em diferentes modos de endereçamento 
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
        }
        return "-100";
    }

    public static int getInstructionSize(String instruction){
        StringBuilder binary = new StringBuilder(); //  cria uma var stringbuild 
        for(int i = 0; i < instruction.length(); i++){  // laço para converter o 
            String binSegment = Integer.toBinaryString(Integer.parseInt(instruction.substring(i, i + 1), 16));
            binSegment = "0000" + binSegment;
            binary.append(binSegment.substring(binSegment.length() - 4));
        }
        int size = binary.toString().length(); 
        if(size == 20)
            size += 4;
        return size;
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

