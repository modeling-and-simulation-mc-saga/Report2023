package network;

import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class representing a network
 *
 * @author tadaki
 */
public abstract class AbstractNetwork {

    public final String label;
    public final boolean directed;//directed or not
    protected final List<Node> nodes;
    protected final List<Edge> edges;
    protected final Map<Node, List<Edge>> node2Edge;//a list of edges starting a node

    public AbstractNetwork(String label, boolean directed) {
        this.label = label;
        this.directed = directed;
        nodes = Collections.synchronizedList(new ArrayList<>());
        node2Edge = Collections.synchronizedMap(new HashMap<>());
        edges = Collections.synchronizedList(new ArrayList<>());
    }

    public AbstractNetwork(String label) {
        this(label, false);
    }

    public void addNode(Node node) {
        if (!nodes.contains(node)) {
            nodes.add(node);
        }
    }

    public void addNode(String label) {
        addNode(new Node(label));
    }

    /**
     * Add an edge connecting two nodes
     *
     * @param n1
     * @param n2
     * @return
     */
    public Edge connectNodes(Node n1, Node n2) {
        //Generate a label for the edge
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(n1.label).append(",");
        sb.append(n2.label).append(")");
        return connectNodes(n1, n2, sb.toString());
    }

    /**
     * Add an edge connecting two nodes
     *
     * @param from
     * @param to
     * @param label
     * @return
     */
    public Edge connectNodes(Node from, Node to, String label) {
        if (nodes.contains(from) && nodes.contains(to)) {
            Edge edge = new Edge(label, directed);
            if (directed) {
                if (node2Edge.containsKey(from)) {
                    node2Edge.get(from).add(edge);
                } else {
                    List<Edge> edgesLocal
                            = Collections.synchronizedList(new ArrayList<>());
                    edgesLocal.add(edge);
                    node2Edge.put(from, edgesLocal);
                }
            } else {
                if (node2Edge.containsKey(from)) {
                    node2Edge.get(from).add(edge);
                } else {
                    List<Edge> edgesLocal
                            = Collections.synchronizedList(new ArrayList<>());
                    edgesLocal.add(edge);
                    node2Edge.put(from, edgesLocal);
                }
                if (node2Edge.containsKey(to)) {
                    node2Edge.get(to).add(edge);
                } else {
                    List<Edge> edgesLocal = Collections.synchronizedList(new ArrayList<>());
                    edgesLocal.add(edge);
                    node2Edge.put(to, edgesLocal);
                }

            }
            edge.setEnd(from, to);
            edges.add(edge);
            return edge;
        }
        return null;
    }

    /**
     * Abstract method for creating a network
     *
     */
    abstract public void createNetwork();

    // Getters and Setters
    public List<Edge> getEdges(Node node) {
        if (nodes.contains(node)) {
            return node2Edge.get(node);
        }
        return null;
    }

    public String getLabel() {
        return label;
    }

    public int getNumNode() {
        return nodes.size();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Node> neighbours(Node node) {
        List<Node> neighbours = Collections.synchronizedList(new ArrayList<>());
        for (Edge edge : node2Edge.get(node)) {
            neighbours.add(edge.getAnotherEnd(node));
        }
        return neighbours;
    }

    public boolean isNeighbour(Node n1, Node n2) {
        for (Edge edge : node2Edge.get(n1)) {
            if (edge.getAnotherEnd(n1).equals(n2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sample main
     *
     * @param args
     * @throws IOException
     */
    static public void main(String args[]) throws IOException {
        //Create an instance of an abstract class
        AbstractNetwork network = new AbstractNetwork("testNetwork", true) {
            //Implementation for the abstract method
            @Override
            public void createNetwork() {
                int n = 3;
                for (int i = 0; i < n; i++) {
                    addNode(new Node(String.valueOf(i)));
                }
                connectNodes(nodes.get(0), nodes.get(1));
                connectNodes(nodes.get(1), nodes.get(2));
                connectNodes(nodes.get(2), nodes.get(0));
            }
        };
        network.createNetwork();
        //Output pajek data
        NetworkFile.outputPajekData(network.getLabel() + ".net", network);
    }
}
