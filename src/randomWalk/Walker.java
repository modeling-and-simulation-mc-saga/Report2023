package randomWalk;

import java.util.List;
import java.util.Random;
import network.*;

/**
 * Walker class
 *
 * @author tadaki
 */
public class Walker {

    final private AbstractNetwork network;
    final Random random;
    private Node currentNode;

    public Walker(AbstractNetwork network, Random random) {
        this.network = network;
        this.random = random;
    }

    /**
     * Set a start node
     *
     * @param start
     */
    public void initialize(Node start) {
        currentNode = start;
    }

    public Node oneStep() {
        List<Node> neighbors = network.neighbours(currentNode);
        int k = random.nextInt(neighbors.size());
        currentNode = neighbors.get(k);
        return currentNode;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

}
