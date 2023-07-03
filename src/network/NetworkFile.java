package network;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 *
 * @author tadaki
 */
public class NetworkFile {
    //Line break character, which is obtained as the property depending on OS
    static final private String NL = System.getProperty("line.separator");

    /**
     * Generate pajek data
     *
     * @param network
     * @return
     */
    public static String generatePajekData(AbstractNetwork network) {
        List<Node> nodes = network.getNodes();
        StringBuilder sb = new StringBuilder();
        //List of nodes
        sb.append("*Vertices ").append(nodes.size()).append(NL);
        nodes.stream().forEach((node) -> {
            int i = nodes.indexOf(node) + 1;
            sb.append(i).append(" ").append("\"").append(node.label).
                    append("\"").append(NL);
        });
        if (network.directed) {
            sb.append("*Arcs").append(NL);
            for (Node from : nodes) {
                List<Edge> edges = network.getEdges(from);
                for (Edge edge : edges) {
                    Node to = edge.getTo();
                    int i = nodes.indexOf(from);
                    int j = nodes.indexOf(to);
                    sb.append(i + 1).append(" ");
                    sb.append(j + 1).append(NL);
                }
            }
        } else {
            sb.append("*Edges").append(NL);
            for (Node from : nodes) {
                List<Edge> edges = network.getEdges(from);
                if (edges != null) {
                    for (Edge edge : edges) {
                        Node to = edge.getAnotherEnd(from);
                        int i = nodes.indexOf(from);
                        int j = nodes.indexOf(to);
                        if (j > i) {
                            sb.append(i + 1).append(" ");
                            sb.append(j + 1).append(NL);
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * Output pajek data into file
     *
     * @param filename
     * @param network
     * @throws java.io.IOException
     */
    public static void outputPajekData(
            String filename, AbstractNetwork network) throws IOException {
        String str = generatePajekData(network);
        try ( PrintStream out = new PrintStream(filename)) {
            out.print(str);
        }
    }
}
