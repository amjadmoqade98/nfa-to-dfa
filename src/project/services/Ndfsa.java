package project.services;

import project.model.Bounds;
import project.model.State;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Ndfsa {
    private HashSet<String> letters;
    private Bounds bounds;
    private Table table;
    private LinkedHashMap<String, State> states;

    public Ndfsa(HashSet<String> letters, Bounds bounds, LinkedHashMap<String, State> states) {
        letters.remove("@");
        letters.add("Lambda");
        this.letters = letters;
        this.bounds = bounds;
        this.states = states;
    }

    public HashSet<String> getLetters() {
        return letters;
    }

    public void setLetters(HashSet<String> transactions) {
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

    public void printTable() {
        System.out.println("NDFSA : ");

        if (table == null) {
            table = new Table(letters.size()+1);

            // first row
            table.appendRow();
            table.appendColum("");
            letters.forEach(l -> table.appendColum(l));

            // row for each state
            this.states.forEach((s, state) -> {
                table.appendRow();
                table.appendColum(s);
                Iterator<String> lettersIterator = letters.iterator();
                while (lettersIterator.hasNext()) {

                    String l = lettersIterator.next();
                    l = (l.equals("Lambda"))? "@" : l ;
                    if (state.getTransactions().indexOf(l) == -1) {
                        table.appendColum(" ");
                    } else {
                        String row = "{";
                        boolean firstT = true;
                        for (int j = 0; j < state.getTransactions().size(); j++) {
                            if (state.getTransactions().get(j).equals(l)) {
                                row = (firstT) ? row : row + " ,";
                                row += state.getNext().get(j).getState();
                                firstT = false;
                            }
                        }
                        row += "}";
                        table.appendColum(row);
                    }
                }
            });
        }
        System.out.println(table);
    }
}
