package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Loader {
    //  Metodo responsável por carregar um programa para a memória do simulador 
    //  Recebe como parametro uma instancia da classe computer e o nome do arquivo contendo o programa
    public static void loadProgram(Computer computer, String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int address = 0;
        //  inicia um laço para armazenar cada linha lida do arquivo e uma varíavel addres para 
        //  rastrear o endereço de memória onde cada linha será escrita
        while((line = reader.readLine()) != null){
            computer.writeInstructionsMemory(address, line);
            address++;
        }
        //  fecha o leitor
        reader.close();
    }
}
