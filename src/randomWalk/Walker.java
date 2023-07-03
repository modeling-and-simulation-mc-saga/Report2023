package randomWalk;

import java.util.List;
import java.util.Random;
import network.*;

/**
 * Walker class
 *
 */
public class Walker {

    final private AbstractNetwork network;
    final Random random;
    private Node currentNode;

    public Walker(AbstractNetwork network, Random random) {

        
    }

    /**
     * Set a start node
     *
     * @param start
     */
    public void initialize(Node start) {

    }

    public Node oneStep() {

        
        return currentNode;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

}
