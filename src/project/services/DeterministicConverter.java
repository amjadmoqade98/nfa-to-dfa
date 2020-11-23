package project.services;

import project.model.Bounds;
import project.model.State;

import java.util.*;

public enum DeterministicConverter {
    INSTANCE;
    private Bounds bounds;
    private Set<String> letters;
    private Map<String, State> states;
    private Set<State> finalStates;

    public static DeterministicConverter createDFSA(Set<String> letters, Map<String, State> states, Bounds bounds) {
        INSTANCE.letters = letters;
        INSTANCE.states = states;
        INSTANCE.bounds = bounds;
        return INSTANCE ;
    }

    public void removeLambdaTransactions() {
        Map<State, Map<String, Set<State>>> lambdaCache;

        refreshFinalStates();
        lambdaCache = new HashMap<State, Map<String, Set<State>>>();
        states.forEach((s, state) -> {
            if (lambdaCache.get(state) == null) {
                mergeLambda(state, lambdaCache);
            }
        });

        this.states.forEach((s, state) -> {
            state.getTransactions().clear();
            state.setTransactions(lambdaCache.get(state));
        });

        this.letters.remove("Lambda");
    }

    private Map<String, Set<State>> mergeLambda(State state, Map<State, Map<String, Set<State>>> lambdaCache) {
        if (!state.getTransactions().keySet().contains("@")) {
            lambdaCache.put(state, (Map) ((LinkedHashMap)state.getTransactions()).clone());
            return state.getTransactions();
        }

        Map<String, Set<State>> transactions = getAllStateTransactions(state);

        state.setVisiting(true);
        boolean ready = true;
        for (State s : state.getTransactions().get("@")) {
            if (!s.isVisiting()) {
                mergeLambda(s, lambdaCache).forEach((s1, states1) -> {
                    if (transactions.get(s1) == null) {
                        transactions.put(s1, new LinkedHashSet<State>());
                    }
                    transactions.get(s1).addAll(states1);
                });
            } else {
                ready = false;
            }
        }
        if (ready) {
            lambdaCache.put(state, transactions);
        }

        state.setVisiting(false);
        return transactions;
    }


    public void removeOfNonDeterminism() {
        boolean NonDeterminism = true;
        while (NonDeterminism) {
            Map<String, State> cloneStates = (Map) ((LinkedHashMap)this.states).clone();
            NonDeterminism = false;
            for (State state : cloneStates.values()) {
                for (String s1 : state.getTransactions().keySet()) {
                    Set<State> states1 = state.getTransactions().get(s1);
                    if (states1.size() > 1) {
                        NonDeterminism = true;
                        boolean isFinalState = false;
                        Map<String, Set<State>> newTransactions = new LinkedHashMap<String, Set<State>>();
                        Iterator<State> iterator = states1.iterator();
                        State tempState = iterator.next();
                        if (finalStates.contains(tempState)) {
                            isFinalState = true;
                        }
                        for (String letter : tempState.getTransactions().keySet()) {
                            newTransactions.put(letter, tempState.getTransactions().get(letter));
                        }
                        String name = tempState.getState();
                        while (iterator.hasNext()) {
                            tempState = iterator.next();
                            if (finalStates.contains(tempState)) {
                                isFinalState = true;
                            }

                            for (String letter : tempState.getTransactions().keySet()) {
                                if (!newTransactions.containsKey(letter)) {
                                    newTransactions.put(letter, (Set) tempState.getTransactions().get(letter));
                                } else {
                                    newTransactions.get(letter).addAll(tempState.getTransactions().get(letter));
                                }
                            }
                            name += " ," + tempState.getState();
                        }

                        State newState;
                        if (!this.states.containsKey(name)) {
                            newState = new State(name);
                            newState.setTransactions(newTransactions);
                            if (!this.states.containsKey(name)) {
                                if (isFinalState) {
                                    finalStates.add(newState);
                                }
                                this.states.put(name, newState);
                            }
                        } else {
                            newState = this.states.get(name);
                        }
                        states1.clear();
                        states1.add(newState);
                    }
                }
            }
        }
    }

    public void removalNonAccessibleStates() {
        this.resetVisited();
        HashSet<String> accessible = new HashSet<>();
        findAccessible(accessible, this.bounds.getstartState());

        System.out.println(accessible);
        Map<String, State> cloneStates =(Map)((LinkedHashMap)this.states).clone();
        cloneStates.forEach((s, state) -> {
            if (!accessible.contains(s)) {
                this.states.remove(state.getState());
                this.finalStates.remove(state);
            }
        });
    }

    public void findAccessible(HashSet<String> accessible, State start) {
        if (start.isVisited()) {
            return;
        }
        accessible.add(start.getState());
        start.setVisited(true);
        start = this.states.get(start.getState());
        start.getTransactions().forEach((s, states1) -> {
            states1.forEach(state -> {
                findAccessible(accessible, state);
            });
        });
    }

    private Map<String, Set<State>> getAllStateTransactions(State state) {
        Map<String, Set<State>> transactions = new LinkedHashMap<String, Set<State>>();
        state.getTransactions().forEach((s, states1) -> {
            if (!s.equals("@")) {
                transactions.put(s, states1);
            }
        });
        return transactions;
    }

    public void refreshNames(Set<String> oldNames) {
        char newName = 'A';
        Map<String, State> cloneStates = (Map) ((LinkedHashMap)this.states).clone();
        for (String s : cloneStates.keySet()) {
            if (s.length() > 1) {
                while (oldNames.contains("" + newName)) newName++;
                State temps = this.states.get(s);
                temps.setState("" + newName);
                this.states.remove(s);
                this.states.put("" + newName, temps);
                oldNames.add("" + newName);
            }
        }
    }

    private void refreshFinalStates() {
        if (this.finalStates == null) {
            this.finalStates = new LinkedHashSet<>();
            this.finalStates.add(bounds.getEndState());
        }
        this.states.forEach((s, state) -> {
            if (state.getTransactions().get("@") != null) {
                if (!Collections.disjoint(state.getTransactions().get("@"), finalStates)) {
                    this.finalStates.add(state);
                }
            }
        });
    }


    private void resetVisited() {
        this.states.forEach((s, state) -> {
            state.setVisiting(false);
            state.setVisited(false);
        });
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
