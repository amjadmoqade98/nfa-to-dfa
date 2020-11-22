package project.utils;

import project.model.Bounds;
import project.model.State;
import project.services.NDFSA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NDFSAFileParser {

    public static NDFSA parseFile(String fileUrl) throws IOException {

        File file = new File(fileUrl);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        LinkedHashSet<String> letters = new LinkedHashSet<>();
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
                String[] letters1 = st.split(" ");
                letters.addAll(Arrays.asList(letters1));
                isThirdLine = false;
            } else {
                String[] transaction = st.split(" ");
                states.get(transaction[0]).addTransaction(transaction[2],
                        states.get(transaction[1]));
                if (bounds.getStartNode() == null) {
                    bounds.setStartNode(states.get(transaction[0]));
                }
            }
        }

        return new NDFSA(letters, bounds, states);
    }
}
