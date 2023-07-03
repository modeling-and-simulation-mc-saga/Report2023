package networkModels;

import java.io.IOException;
import java.util.Random;
import network.*;

/**
 * Erdos-Renni random network
 *
 * @author tadaki
 */
public class ER extends AbstractNetwork {

    private final int numNodes;//The number of nodes
    private final int numEdges;//The number of edges
    private final Random myRandom;

    /**
     * Constructor
     *
     * @param numNodes
     * @param numEdges
     * @param myRandom
     */
    public ER(int numNodes, int numEdges, Random myRandom) {
        super("ER(" + String.valueOf(numNodes) + ","
                + String.valueOf(numEdges) + ")");
        this.numNodes = numNodes;
        this.numEdges = numEdges;
        this.myRandom = myRandom;
    }

    @Override
    public void createNetwork() {
        createNodes();
        createEdges();
    }

    /**
     * Create nodes
     */
    private void createNodes() {
        for (int i = 0; i < numNodes; i++) {
            addNode(new Node(String.valueOf(i)));
        }
    }

    /**
     * Create edges randomly
     */
    private void createEdges() {
        for (int i = 0; i < numEdges; i++) {
            int x = (int) (numNodes * myRandom.nextDouble());
            int y = (int) (numNodes * myRandom.nextDouble());
            connectNodes(nodes.get(x), nodes.get(y));
        }
    }

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        int n = 100;
        long seed = 938472L;
        double pd[] = {0.005, 0.009, 0.01, .011, 0.02, 0.03, .04};
        for (double p : pd) {
            int numEdges = (int) (p * n * (n - 1) / 2.);
            ER er = new ER(n, numEdges, new Random(seed));
            er.createNetwork();
            String filename = "er-" + String.valueOf(p) + ".net";
            NetworkFile.outputPajekData(filename, er);
        }
    }
}
