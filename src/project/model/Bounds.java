package project.model;

public class Bounds implements Cloneable {
    private State startNode;
    private State endNode;

    public Bounds() {
    }
    public State getStartNode() {
        return startNode;
    }

    public void setStartNode(State startNode) {
        this.startNode = startNode;
    }

    public State getEndNode() {
        return endNode;
    }

    public void setEndNode(State endNode) {
        this.endNode = endNode;
    }

    @Override
    public Bounds clone() {
        Bounds b = new Bounds();
        b.setStartNode(this.getStartNode());
        b.setEndNode(this.getEndNode());
        return b ;
    }
}
