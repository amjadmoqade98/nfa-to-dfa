package project.services;

import project.model.State;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public enum StringValidator {
    INSTANCE;

    private LinkedHashSet<String> letters;
    private LinkedHashMap<String, State> states;
    private State finalStates;



    public boolean isValid(String input) {

        return true;
    }

    public void setLetters(LinkedHashSet<String> letters) {
        this.letters = letters;
    }

    public void setStates(LinkedHashMap<String, State> states) {
        this.states = states;
    }

    public void setFinalStates(State finalStates) {
        this.finalStates = finalStates;
    }
}
