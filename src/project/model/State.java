package project.model;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class State {

    private String state ;
    private boolean visiting;
    private boolean visited;
    private LinkedHashMap<String, LinkedHashSet<State>> transactions ;

    public State(String state) {
        this.state = state;
        this.transactions = new LinkedHashMap<>();
    }

    public void addTransaction(String letter , State next) {
        if(transactions.get(letter) == null) {
            transactions.put(letter, new LinkedHashSet<>());
        }
       this.transactions.get(letter).add(next);
    }

    public void setTransactions(LinkedHashMap<String, LinkedHashSet<State>> transactions) {
        this.transactions = transactions;
    }

    public LinkedHashMap<String,  LinkedHashSet<State>> getTransactions() {
        return transactions;
    }

    public String getState() {
        return state;
    }

    public boolean isVisiting() {
        return visiting;
    }

    public void setVisiting(boolean visiting) {
        this.visiting = visiting;
    }


    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setState(String state) {
        this.state = state;
    }
}
