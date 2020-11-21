package project;

import project.services.Dfsa;
import project.services.Ndfsa;
import project.utils.FileParser;

import java.io.*;


public class Main {
    static  int index = 0 ;
    public static void main(String []args) throws IOException {
        Ndfsa ndfsa = FileParser.INSTANCE.parseFile("src\\resources\\data.txt");
//        ndfsa.getStates().forEach((s, state) -> {
//            System.out.println(s);
//        });
        ndfsa.printTable();
        Dfsa dfsa= new Dfsa(ndfsa.getLetters() , ndfsa.getStates() , ndfsa.getBounds());
        dfsa.removeLambda();
    }
}

