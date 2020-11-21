package project.utils;

import project.model.Bounds;
import project.model.State;
import project.services.Ndfsa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public enum FileParser {
    INSTANCE;

    public Ndfsa parseFile(String fileUrl) throws IOException {

        File file = new File("src\\resources\\data.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        HashSet<String> letters = new HashSet<>();
        LinkedHashMap<String, State> states = new LinkedHashMap<>();
        Bounds bounds = new Bounds();

        /** File Template
         * S A B C D E F G H // States
         * H                // Final State
         * l d @           // Transactions
         * S A l          // transitions between states
         * A B @
         *
         *
         *
         */
        boolean isFirstLine = true, isSecondLine = true, isThirdLine = true;
        while ((st = br.readLine()) != null) {
            if (isFirstLine) {
                String[] statesS = st.split(" ");
                for (int i = 0; i < statesS.length; i++) {
                    State s = new State(statesS[i]);
                    states.put(statesS[i], s);
                }
                isFirstLine = false;
            } else if (isSecondLine) {
                bounds.setEndNode(states.get(st));
                isSecondLine = false;
            } else if (isThirdLine) {
                String[] transactionsS = st.split(" ");
                for (int i = 0; i < transactionsS.length; i++) {
                    letters.add(transactionsS[i]);
                }
                isThirdLine = false;
            } else {
                String[] Transaction = st.split(" ");
                states.get(Transaction[0]).addTransaction(Transaction[2],
                        states.get(Transaction[1]));
                if (bounds.getStartNode() == null) {
                    bounds.setStartNode(states.get(Transaction[0]));
                }
            }
        }
        Ndfsa ndfsa = new Ndfsa(letters, bounds, states);

        return ndfsa;
    }
}
