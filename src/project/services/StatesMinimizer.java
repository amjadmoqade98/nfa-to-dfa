package project.services;

import project.model.State;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StatesMinimizer {
    private LinkedHashSet<String> letters;
    private LinkedHashMap<String, State> states;
    private LinkedHashSet<State> finalStates;

    public StatesMinimizer(LinkedHashSet<String> letters, LinkedHashMap<String, State> states, LinkedHashSet<State> finalStates) {
        this.letters = letters;
        this.states = states;
        this.finalStates = finalStates;
    }

    class Pair {
        State s1;
        State s2;

        public Pair(State p, State q) {
            this.s1 = p;
            this.s2 = q;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Pair) {
                if (((Pair) o).s1 == this.s1 && ((Pair) o).s2 == this.s2) {
                    return true;
                }

                if (((Pair) o).s1 == this.s2 && ((Pair) o).s2 == this.s1) {
                    return true;
                }
            }
            return false;
        }


    }

    public void minimizeStates() {

        // finding feasible pairs
        ArrayList<Pair> pairs = new ArrayList<>();
        this.states.forEach((s, state) -> {
            this.states.forEach((s1, state1) -> {
                if (state != state1) {
                    if ((finalStates.contains(state) && finalStates.contains(state1)) || (!finalStates.contains(state) && !finalStates.contains(state1))) {
                        if (state.getTransactions().keySet().equals(state1.getTransactions().keySet())) {
                            if (!isPairExist(state, state1, pairs)) {
                                pairs.add(new Pair(state, state1));
                            }
                        }
                    }
                }
            });
        });
        ArrayList<Pair> markedPairs = new ArrayList<>();

        pairs.forEach(pair -> {
            AtomicBoolean equivalent = new AtomicBoolean(true);
            pair.s1.getTransactions().forEach((s, states1) -> {
                if (equivalent.get() == true) {
                    State tempState1 = new ArrayList<>(states1).get(0);
                    State tempState2 = new ArrayList<>(pair.s2.getTransactions().get(s)).get(0);
                    if (tempState1 != tempState2) {
                        if (!isPairExist(tempState1, tempState2, pairs) && !isPairExist(tempState1, tempState2, markedPairs)) {
                            equivalent.set(false);
                            markedPairs.add(pair);
                        }
                    }
                }

            });
        });
        System.out.println();

        //finding pairs with equivalent states

        boolean isAllRemoved = false;
        while (!isAllRemoved) {

        }
    }



    public boolean isPairExist(State p, State q, List<Pair> pairs) {
        Pair temp = new Pair(p, q);
        for (Pair pair : pairs) {
            if (temp.equals(pair)) return true;
        }
        return false;
    }

    public LinkedHashSet<String> getLetters() {
        return letters;
    }

    public LinkedHashMap<String, State> getStates() {
        return states;
    }
}
