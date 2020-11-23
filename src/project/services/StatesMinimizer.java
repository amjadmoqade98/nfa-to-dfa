package project.services;

import project.model.State;
import java.util.*;

public enum StatesMinimizer {
    INSTANCE;

    private Set<String> letters;
    private Map<String, State> states;
    private Set<State> finalStates;
    private State startState;

    public static StatesMinimizer createMinimizer(Set<String> letters, Map<String, State> states, Set<State> finalStates, State startState) {
        INSTANCE.letters = letters;
        INSTANCE.states = states;
        INSTANCE.finalStates = finalStates;
        INSTANCE.startState = startState;

        return INSTANCE;
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
        List<Pair> pairs = new ArrayList<>();
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

        //mark all the non equivalent pairs
        List<Pair> markedPairs = new ArrayList<>();
        boolean isAllRemoved = false;
        while (!isAllRemoved) {
            isAllRemoved = true;
            for (Pair pair : pairs) {
                boolean equivalent = true;
                // loop over the the pair transaction looking for non equivalent transaction
                for (Map.Entry<String, Set<State>> transaction : pair.s1.getTransactions().entrySet()) {
                    if (equivalent == true) {
                        State tempState1 = transaction.getValue().iterator().next();
                        State tempState2 = pair.s2.getTransactions().get(transaction.getKey()).iterator().next();
                        if (tempState1 != tempState2) {
                            if (!isPairExist(tempState1, tempState2, pairs) || isPairExist(tempState1, tempState2, markedPairs)) {
                                equivalent = false;
                                markedPairs.add(pair);
                                isAllRemoved = false;
                            }
                        }
                    }
                }
            }
        }

        pairs.removeAll(markedPairs);


        // to avoid removing the start state
        for (Pair p : pairs) {
            if (p.s1.equals(startState)) {
                State temp = p.s1;
                p.s1 = p.s2;
                p.s2 = temp;
            }
        }

        // remove one of each equivalent states
        pairs.forEach(pair -> {
            this.states.remove(pair.s1.getState());
            this.finalStates.remove(pair.s1);
        });

        // replace any removed state from the other states transactions
        pairs.forEach(pair -> {
            states.forEach((s, state) -> {
                for (Map.Entry<String, Set<State>> transaction : state.getTransactions().entrySet()) {
                    if (transaction.getValue().iterator().next().equals(pair.s1)) {
                        transaction.getValue().remove(pair.s1);
                        transaction.getValue().add(pair.s2);
                    }
                }
            });
        });
    }

    public boolean isPairExist(State p, State q, List<Pair> pairs) {
        Pair temp = new Pair(p, q);
        for (Pair pair : pairs) {
            if (temp.equals(pair)) return true;
        }
        return false;
    }

    public Set<String> getLetters() {
        return letters;
    }

    public Map<String, State> getStates() {
        return states;
    }

    public Set<State> getFinalStates() {
        return finalStates;
    }
}
