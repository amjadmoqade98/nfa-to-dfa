package project;

import project.services.DeterministicConverter;
import project.model.NDFSA;
import project.services.StatesMinimizer;
import project.services.StringValidator;
import project.utils.Table;
import project.utils.NDFSAParser;

import java.io.*;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws IOException {
        NDFSA ndfsa = NDFSAParser.parseFile("src\\resources\\data.txt");
        HashSet<String> initialStates = new HashSet<String>(ndfsa.getStates().keySet());

        System.out.println("NDFSA : ");
        Table.printStateMachine(ndfsa.getStates(), ndfsa.getLetters(), ndfsa.getBounds().getstartState(),
                ndfsa.getBounds().getEndState());

        DeterministicConverter deterministicConverter = DeterministicConverter.createDFSA(ndfsa.getLetters(),
                ndfsa.getStates(), ndfsa.getBounds());

        deterministicConverter.removeLambdaTransactions();

        System.out.println("Dfsa After removing lambda Transactions : ");
        Table.printStateMachine(deterministicConverter.getStates(),
                deterministicConverter.getLetters(), ndfsa.getBounds().getstartState(),
                deterministicConverter.getFinalStates());

        deterministicConverter.removeOfNonDeterminism();

        System.out.println("Dfsa After removing Of Non Determinism");
        Table.printStateMachine(deterministicConverter.getStates(),
                ndfsa.getLetters(), ndfsa.getBounds().getstartState(), deterministicConverter.getFinalStates());

        deterministicConverter.removalNonAccessibleStates();

        System.out.println("Dfsa After removing Non Accessible States");
        Table.printStateMachine(deterministicConverter.getStates(), ndfsa.getLetters(), ndfsa.getBounds().getstartState(),
                deterministicConverter.getFinalStates());

        deterministicConverter.refreshNames(initialStates);
        System.out.println("Dfsa After refersh names");
        Table.printStateMachine(deterministicConverter.getStates(), ndfsa.getLetters(), ndfsa.getBounds().getstartState(),
                deterministicConverter.getFinalStates());


        StatesMinimizer minimizer = StatesMinimizer.createMinimizer(ndfsa.getLetters(), deterministicConverter.getStates(),
                deterministicConverter.getFinalStates(), ndfsa.getBounds().getstartState());
        minimizer.minimizeStates();

        System.out.println("After Minimizing DFSA");
        Table.printStateMachine(minimizer.getStates(), minimizer.getLetters(), ndfsa.getBounds().getstartState(),
                deterministicConverter.getFinalStates());


        // validating strings
        StringValidator validator = StringValidator.createValidator(ndfsa.getBounds().getstartState(), ndfsa.getLetters(),
                minimizer.getStates(), minimizer.getFinalStates());
        validator.validationLifeCycle();
    }
}

