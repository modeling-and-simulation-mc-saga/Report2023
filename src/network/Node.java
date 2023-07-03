package network;

/**
 * Class representing a node
 * @author tadaki
 */
public class Node implements Comparable{
    public final String label;

    /**
     * 
     * @param label label of this node
     */
    public Node(String label) {
        this.label = label;
    }

    @Override
    public int compareTo(Object o) {
        return label.compareTo(((Node)o).label);
    }
    
}
