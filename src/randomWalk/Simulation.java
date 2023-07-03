package randomWalk;

import java.awt.Point;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import network.*;
import networkModels.BA;

/**
 *
 * @author tadaki
 */
public class Simulation {

    private final AbstractNetwork network;
    private final List<Walker> walkers;

    public Simulation(AbstractNetwork network, int numWalkers, Random random) {
        this.network = network;
        walkers = new ArrayList<>();
        List<Node> nodes = network.getNodes();
        for (int i = 0; i < numWalkers; i++) {
            Walker w = new Walker(network, random);
            int k = random.nextInt(nodes.size());
            w.initialize(nodes.get(k));
            walkers.add(w);
        }
    }

    public void oneStep() {
        for (Walker w : walkers) {
            w.oneStep();
        }
    }

    public Map<Node, Integer> observe() {
        Map<Node, Integer> map = new HashMap<>();
        for (Node n : network.getNodes()) {
            map.put(n, 0);
        }
        for (Walker w : walkers) {
            Node n = w.getCurrentNode();
            int k = map.get(n);
            map.put(n, k + 1);
        }
        return map;
    }

    public List<Point> degreeFrequency() {
        Map<Node, Integer> map = observe();
        List<Point> relation = new ArrayList<>();
        for (Node n : map.keySet()) {
            int degree = network.neighbours(n).size();
            relation.add(new Point(degree, map.get(n)));
        }
        return relation;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        int n = 100;
        int m = 1;
        long seed = 938472L;
        Random random = new Random(seed);
        BA network = new BA(n, m, random);
        network.createNetwork();

        int numWalkers = 10000;
        int tMax = 10000;
        Simulation sys = new Simulation(network, numWalkers, random);
        for (int t = 0; t < tMax; t++) {
            sys.oneStep();
        }
        List<Point> relation = sys.degreeFrequency();
        try (PrintStream out = new PrintStream("output.txt")) {
            for (Point p : relation) {
                out.println(p.x + " " + p.y);
            }
        }
    }

}
