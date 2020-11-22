package project.services;

import project.model.Bounds;
import project.model.State;
import project.utils.Table;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class NDFSA {
    private LinkedHashSet<String> letters;
    private Bounds bounds;
    private Table table;
    private LinkedHashMap<String, State> states;

    public NDFSA(LinkedHashSet<String> letters, Bounds bounds, LinkedHashMap<String, State> states) {
        letters.remove("@");
        letters.add("Lambda");
        this.letters = letters;
        this.bounds = bounds;
        this.states = states;
    }

    public LinkedHashSet<String> getLetters() {
        return letters;
    }

    public void setLetters(LinkedHashSet<String> transactions) {
        this.letters = transactions;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public LinkedHashMap<String, State> getStates() {
        return states;
    }

    public void setStates(LinkedHashMap<String, State> states) {
        this.states = states;
    }

}
