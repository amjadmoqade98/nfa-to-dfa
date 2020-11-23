package project.model;

import project.model.Bounds;
import project.model.State;
import java.util.Map;
import java.util.Set;

public class NDFSA {
    private Set<String> letters;
    private Bounds bounds;
    private Map<String, State> states;

    public NDFSA(Set<String> letters, Bounds bounds, Map<String, State> states) {
        letters.remove("@");
        letters.add("Lambda");
        this.letters = letters;
        this.bounds = bounds;
        this.states = states;
    }

    public Set<String> getLetters() {
        return letters;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public Map<String, State> getStates() {
        return states;
    }

}
