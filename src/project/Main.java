package project;

import project.model.State;
import project.services.DFSA;
import project.services.NDFSA;
import project.services.StatesMinimizer;
import project.utils.Table;
import project.utils.NDFSAFileParser;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedHashMap;


public class Main {
    static int index = 0;
    static  LinkedHashMap<String, State> states;

    public static void main(String[] args) throws IOException {
        NDFSA ndfsa = NDFSAFileParser.parseFile("src\\resources\\data.txt");
        HashSet<String> initialStates  = new HashSet<String> (ndfsa.getStates().keySet());

        states = ndfsa.getStates();
        System.out.println("NDFSA : ");
        Table.INSTANCE.printStateMachine(ndfsa.getStates(), ndfsa.getLetters(), ndfsa.getBounds().getStartNode(),
                ndfsa.getBounds().getEndNode());

        DFSA dfsa = new DFSA(ndfsa.getLetters(), ndfsa.getStates(), ndfsa.getBounds());
        states = dfsa.getStates();

        dfsa.removeLambdaTransactions();
        states = dfsa.getStates();

        System.out.println("Dfsa After removing lambda Transactions : ");
        Table.INSTANCE.printStateMachine(dfsa.getStates(), dfsa.getLetters(), ndfsa.getBounds().getStartNode(),
                dfsa.getFinalStates());

        dfsa.removeOfNonDeterminism();
        states = dfsa.getStates();

        System.out.println("Dfsa After removing Of Non Determinism");
        Table.INSTANCE.printStateMachine(dfsa.getStates(), ndfsa.getLetters(), ndfsa.getBounds().getStartNode(),
                dfsa.getFinalStates());

        dfsa.removalNonAccessibleStates();
        states = dfsa.getStates();

        System.out.println("Dfsa After removing Non Accessible States");
        Table.INSTANCE.printStateMachine(dfsa.getStates(), ndfsa.getLetters(), ndfsa.getBounds().getStartNode(),
                dfsa.getFinalStates());


        dfsa.refreshNames(initialStates);
        states = dfsa.getStates();

        System.out.println("Dfsa After refersh names");
        Table.INSTANCE.printStateMachine(dfsa.getStates(), ndfsa.getLetters(), ndfsa.getBounds().getStartNode(),
                dfsa.getFinalStates());


        StatesMinimizer minimizer = new StatesMinimizer(ndfsa.getLetters() , dfsa.getStates() , dfsa.getFinalStates());
        minimizer.minimizeStates();

        System.out.println("After Minimizing DFSA");
        Table.INSTANCE.printStateMachine(minimizer.getStates(), minimizer.getLetters(), ndfsa.getBounds().getStartNode(),
                dfsa.getFinalStates());
    }
}

