package project.model;

import java.util.ArrayList;
import java.util.List;

public class State {

    private String state ;
    private boolean visited;

    private List<State> next ;
    private List<String> transactions ;

    public State(String state) {
        this.state = state;
        this.transactions = new ArrayList<>();
        this.next = new ArrayList<>();
    }

    public void addTransaction(String letter , State next) {
        this.next.add(next);
        this.transactions.add(letter);
    }

    public String getState() {
        return state;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public List<State> getNext() {
        return next;
    }

    public void setNext(List<State> next) {
        this.next = next;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }
}
