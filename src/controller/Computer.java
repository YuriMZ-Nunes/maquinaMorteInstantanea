package controller;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Computer {
    //  Indica que instructionsMemory é um Array de strings - armazena uma coleção de elementos do mesmo tipo
    public static String[] instructionsMemory;
    //  é uma lista que contém elementos do tipo Integer
    //  uma lista é uma estrutura de dados que permite armazenar e manipular um sequência ordenada de elementos
    //  ArrayList diz que identifica a configuração da lista para ser manipulada rapidamente
    public static List<Integer> opcodeListFormatSize6 = new ArrayList<Integer>();  
    //  A var estatica memory é do tipo Map<String, Integer>, ou seja um 
    //  mapa onde a chave são String e os valores associados são Integer
    //  a escolha do LinkedHashMap para representar a memoria faz com que
    //  ao iterar sobre ela, os elementos são acessados na ordem em que foram inseridos
    public static Map<String, Integer> memory = new LinkedHashMap<>();
    //  idem á estrutura acima
    //  Contudo, escolhe-se a HashMap pela a efiência no tempo médio de acesso para buscar valores por chave.
    //  Ele não mantém a ordem de inserção dos elementos
    public static Map<String, Integer> registers = new HashMap<>();

    //  implementação do construtor da classe Computer
    //  inicializa o array instructionsMemory com tamanho fixo de 1000 elementos
    //  inicializa os mapas mnemory, registers, lista de opcodes
    public Computer(int memorySize){
        instructionsMemory = new String[1000];
        createMemory(memory);
        createRegisters(registers);
        fillOpcodeListSize6(opcodeListFormatSize6);
    }
    
    //  responsável por ler o valor armazenado em um endereço espefico da memoria
    //  recebe um endereço no formato de String 
    //  verifica se o endereço está no mapa (containsKey)
    //  retorna o valor associado ao endereço contido no mapa (memoria)
    public int readMemory(String address){  
        if(memory.containsKey(address))
            return memory.get(address);
        System.err.println("Erro: Endereço invalido: " + address);
        return -100;
        
    }

    //  responsável pela escrita na memoria
    // recebe o endereço no formato String e o valor associado 
    // verifica se o endereço está na memoria
    // se sim, é adicionado o endereço e o valor 
    public void writeMemory(String address, int value){
        if (memory.containsKey(address))
            memory.put(address, value);
        else 
            System.err.println("Erro: Endereço invalido: " + address);
    }

    //  responsável por escrever no registrador
    //  recebe um nome de registrador e verifica se está no mapa
    //  se sim, é retornado o valor associado ao endereço no registrador
    public int readRegister(String registerString){
        if(registers.containsKey(registerString))
            return registers.get(registerString);
        System.err.println("Erro: Registrador invalido: " + registerString);
        return -100;
    }
    
    //  responsável pela escrita de registrador
    public void writeRegister(String registerString,int value) {
        if(registers.containsKey(registerString)){
            registers.put(registerString, value);
        }
    }
    //  responsável pela leitura de de instruções
    public String readInstructionsMemory(int address){
        return instructionsMemory[address];
    }
    //  responsável pela escrita de instruções no array de instruções
    //  recebe o endereço e codigo em string
    public void writeInstructionsMemory(int address, String line){
        instructionsMemory[address] = line;
    }
    //  responsável por incializar a memória (LinkedMap)
    //  recebe como parametro um mapa onde os endereços são String e os valores 
    //  associado são Integer
    // O laço de repetição "for": é usado para percorrer um intervalo entre 1 até 0xFF
    //  OU TAMBÉM, 4095 em decimal
    public void createMemory(Map<String, Integer> memory){
        for(int i = 1; i <= 0xFFF; i++){
            String position = String.format("0x%03X", i);
            memory.put(position, 0);
        }
        System.out.println(memory);
    }
    //  responsável por incializar os registradores da máquina
    //  recebe como parametro um mapa que representa os registradores
    //  inicializa os registradores com o metódo put
    public void createRegisters(Map<String, Integer> registers){
        registers.put("A", 0);        
        registers.put("X", 0);
        registers.put("L", 0);
        registers.put("B", 0);
        registers.put("S", 0);
        registers.put("T", 0);
        registers.put("PC", 0);
        registers.put("SW", 0); 
    }
    //  recebe uma lista de inteiros como paratametro e adiciona valores hexadecimais a lista
    //  cada linha add adiciona um valor hex á lista opcodeList onde 0xXX é oo numero hex 
    public void fillOpcodeListSize6(List<Integer> opcodeList){
        opcodeList.add(0x18);
        opcodeList.add(0x40);
        opcodeList.add(0x28);
        opcodeList.add(0x24);
        opcodeList.add(0x3C);
        opcodeList.add(0x30);
        opcodeList.add(0x34);
        opcodeList.add(0x38);
        opcodeList.add(0x48);
        opcodeList.add(0x00);
        opcodeList.add(0x68);
        opcodeList.add(0x50);
        opcodeList.add(0x08);
        opcodeList.add(0x6C);
        opcodeList.add(0x74);
        opcodeList.add(0x04);
        opcodeList.add(0x20);
        opcodeList.add(0x44);
        opcodeList.add(0x4C);
        opcodeList.add(0x0C);
        opcodeList.add(0x78);
        opcodeList.add(0x54);
        opcodeList.add(0x14);
        opcodeList.add(0x7C);
        opcodeList.add(0x84);
        opcodeList.add(0x10);
        opcodeList.add(0x1C);
        opcodeList.add(0x2C);
        opcodeList.add(0xB8);
    }
    //  verifica se um opcode especifico possui tamanho de 6 bits
    //  o parametro opCode: recebe um inteiro representando o opcode a ser verificado
    //  se naõ estiver presente, isso indica para o próximo comando deslocar 2 bits para a esquerda e tentar localizá-lo 
    //  pois há instruções com 8 bits (tipo 1)
    public boolean verifyOpcodeSize6(int opCode){
        if(opcodeListFormatSize6.contains(opCode))
            return false;
        if(opcodeListFormatSize6.contains(opCode << 2))
            return true;
        return false;
    }

    
}
