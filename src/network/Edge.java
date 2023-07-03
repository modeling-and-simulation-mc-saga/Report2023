package network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class describing an edge
 *
 * @author tadaki
 */
public class Edge {

    public final boolean directed;//directed or not
    public final String label;
    private Node from;
    private Node to;
    protected double weight;

    /**
     * Constructor
     *
     * @param label label of this edge
     * @param directed directed or not
     */
    public Edge(String label, boolean directed) {
        this.directed = directed;
        this.label = label;
    }

    /**
     * Constructor for undirected network
     *
     * @param label label of this edge
     */
    public Edge(String label) {
        this(label, false);
    }

    /**
     * Set terminal nodes of this edge.
     * If this edge is directed, the order of argument matters.
     *
     * @param from
     * @param to
     */
    public void setEnd(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    /**
     * Return the other terminal node of the given node
     *
     * @param node
     * @return
     */
    public Node getAnotherEnd(Node node) {
        if (node.equals(from)) {
            return to;
        }
        if (node.equals(to)) {
            return from;
        }
        return null;
    }

    public List<Node> getEnds() {
        List<Node> nodes = Collections.synchronizedList(new ArrayList<>());
        nodes.add(from);
        nodes.add(to);
        return nodes;
    }

    /**
     * 
     * Determine if a given node is on this edge
     *
     * @param node
     * @return
     */
    public boolean hasEnd(Node node) {
        return node.equals(from) || node.equals(to);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return label;
    }

}
