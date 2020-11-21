package project.services;

import project.model.Bounds;
import project.model.State;

import java.util.*;

public class Dfsa {
    private Bounds bounds ;
    private HashSet<String> letters;
    private LinkedHashMap<String, State> states;
    private Map<Set<String>, Integer> map;
    private Set<String> lambdaStates;
    private Queue<String> queue = new LinkedList<>();
    private List<String[]> dfa = new ArrayList<>();
    private List<String> endState = new ArrayList<>();

    public Dfsa(HashSet<String> letters ,LinkedHashMap<String, State> states, Bounds bounds ) {
        this.letters = letters;
        this.states = states ;
        this.bounds = bounds;
    }

    public void removeLambda() {
        if(this.letters.("Lambda")){

        }
        states.forEach((s, state) -> {





        });
    }

}
