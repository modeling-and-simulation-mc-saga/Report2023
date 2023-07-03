package networkModels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import network.*;

/**
 * Barabasi-Albert model
 *
 * @author tadaki
 */
public class BA extends AbstractNetwork {

    private final int numNodes;//The number of nodes
    private final int numEdgesFromNewNode;//The number of edges from the new node
    //In this list, a single vertex appears multiple times. 
    //If the degree of a vertex is k, it will appear k times
    private List<Node> targetList;
    private final Random myRandom;

    /**
     * Construstor
     *
     * @param numNodes
     * @param numEdgesFromNewNode
     * @param myRandom
     */
    public BA(int numNodes, int numEdgesFromNewNode, Random myRandom) {
        super("BA(" + String.valueOf(numNodes) + ","
                + String.valueOf(numEdgesFromNewNode) + ")");
        this.numNodes = numNodes;
        this.numEdgesFromNewNode = numEdgesFromNewNode;
        if (numNodes < numEdgesFromNewNode) {
            throw new UnsupportedOperationException(
                    "Can not create such network.");
        }
        targetList = Collections.synchronizedList(new ArrayList<>());
        this.myRandom = myRandom;
    }

    @Override
    public void createNetwork() {
        initialize();
        for (int i = numEdgesFromNewNode + 1; i < numNodes; i++) {
            addNewNode();
        }
    }

    /**
     * Add one node to this network
     */
    private void addNewNode() {
        Node node = new Node(String.valueOf(getNumNode() + 1));
        addNode(node);
        List<Node> target = Collections.synchronizedList(new ArrayList<>());
        int nn = targetList.size();
        //select nodes and store them into target
        for (int i = 0; i < numEdgesFromNewNode; i++) {
            int k = (int) (nn * myRandom.nextDouble());
            target.add(targetList.get(k));
        }
        //Generate edges
        for (Node t : target) {
            connectNodes(node, t);
            targetList.add(t);
            targetList.add(node);
        }
    }

    /**
     * Initialize 
     *
     */
    private void initialize() {
        //Generate m nodes
        for (int i = 0; i <= numEdgesFromNewNode; i++) {
            addNode(new Node(String.valueOf(i)));
        }
        //Connect vertices to each other with double edges.
        for (int i = 0; i <= numEdgesFromNewNode; i++) {
            Node x = nodes.get(i);
            for (int j = 0; j <= numEdgesFromNewNode; j++) {
                Node y = nodes.get(j);
                connectNodes(x, y);
                targetList.add(x);
                targetList.add(y);
            }
        }
    }

    public static void main(String args[]) throws IOException {
        int n = 100;
        int m = 1;
        long seed = 938472L;
        BA ba = new BA(n, m, new Random(seed));
        ba.createNetwork();
        NetworkFile.outputPajekData("ba.net", ba);
    }

}
