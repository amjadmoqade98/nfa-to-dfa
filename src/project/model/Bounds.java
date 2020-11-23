package project.model;

public class Bounds implements Cloneable {
    private State startState;
    private State finalState;

    public Bounds() {
    }
    public State getstartState() {
        return startState;
    }

    public void setstartState(State startState) {
        this.startState = startState;
    }

    public State getEndState() {
        return finalState;
    }

    public void setEndState(State finalState) {
        this.finalState = finalState;
    }

    @Override
    public Bounds clone() {
        Bounds b = new Bounds();
        b.setstartState(this.getstartState());
        b.setEndState(this.getEndState());
        return b ;
    }
}
