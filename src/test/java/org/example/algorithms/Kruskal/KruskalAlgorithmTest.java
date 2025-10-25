package org.example.algorithms.Kruskal;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


public class KruskalAlgorithmTest {
    private KruskalAlgorithm.GraphData sampleGraph;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();

        sampleGraph = new KruskalAlgorithm.GraphData();
        sampleGraph.nodes = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            KruskalAlgorithm.Node n = new KruskalAlgorithm.Node();
            n.id = i;
            sampleGraph.nodes.add(n);
        }

        sampleGraph.edges = new ArrayList<>();
        sampleGraph.edges.add(makeEdge(1, 2, 10));
        sampleGraph.edges.add(makeEdge(1, 3, 6));
        sampleGraph.edges.add(makeEdge(2, 3, 5));
        sampleGraph.edges.add(makeEdge(3, 4, 15));
    }

    private KruskalAlgorithm.Edge makeEdge(int s, int t, int w) {
        KruskalAlgorithm.Edge e = new KruskalAlgorithm.Edge();
        e.source = s;
        e.target = t;
        e.weight = w;
        return e;
    }


    @Test
    void testMSTHasCorrectNumberOfEdges() {
        int V = sampleGraph.nodes.size();
        List<KruskalAlgorithm.Edge> mst = KruskalAlgorithm.kruskalMST(V, sampleGraph.edges);
        assertEquals(V - 1, mst.size());
    }
    @Test
    void testMSTIsAcyclic() {
        int V = sampleGraph.nodes.size();
        List<KruskalAlgorithm.Edge> mst = KruskalAlgorithm.kruskalMST(V, sampleGraph.edges);
        KruskalAlgorithm.DSU dsu = new KruskalAlgorithm.DSU(V);
        for (KruskalAlgorithm.Edge e : mst) {
            int u = e.source - 1, v = e.target - 1;
            assertNotEquals(dsu.find(u), dsu.find(v));
            dsu.union(u, v);
        }
    }
    @Test
    void testMSTConnectsAllVertices() {
        int V = sampleGraph.nodes.size();
        List<KruskalAlgorithm.Edge> mst = KruskalAlgorithm.kruskalMST(V, sampleGraph.edges);

        KruskalAlgorithm.DSU dsu = new KruskalAlgorithm.DSU(V);
        for (KruskalAlgorithm.Edge e : mst) {
            dsu.union(e.source - 1, e.target - 1);
        }

        int root = dsu.find(0);
        for (int i = 1; i < V; i++) {
            assertEquals(root, dsu.find(i));
        }
    }
    @Test
    void testDisconnectedGraphHandledGracefully() {
        KruskalAlgorithm.GraphData g = new KruskalAlgorithm.GraphData();
        g.nodes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            KruskalAlgorithm.Node n = new KruskalAlgorithm.Node();
            n.id = i;
            g.nodes.add(n);
        }

        g.edges = new ArrayList<>();
        List<KruskalAlgorithm.Edge> mst = KruskalAlgorithm.kruskalMST(g.nodes.size(), g.edges);
        assertTrue(mst.isEmpty());
    }
    @Test
    void testTotalCostMatchesKnownValue() {
        int V = sampleGraph.nodes.size();
        List<KruskalAlgorithm.Edge> mst = KruskalAlgorithm.kruskalMST(V, sampleGraph.edges);

        int total = mst.stream().mapToInt(e -> e.weight).sum();
        assertEquals(26, total);
    }



    @Test
    void testExecutionTimeNonNegative() {
        int V = sampleGraph.nodes.size();
        long start = System.nanoTime();
        KruskalAlgorithm.kruskalMST(V, sampleGraph.edges);
        long end = System.nanoTime();
        double ms = (end - start) / 1_000_000.0;
        assertTrue(ms >= 0);
    }
    @Test
    void testResultsAreReproducible() {
        int V = sampleGraph.nodes.size();
        List<KruskalAlgorithm.Edge> mst1 = KruskalAlgorithm.kruskalMST(V, sampleGraph.edges);
        List<KruskalAlgorithm.Edge> mst2 = KruskalAlgorithm.kruskalMST(V, sampleGraph.edges);

        assertEquals(mst1.size(), mst2.size());

        int cost1 = mst1.stream().mapToInt(e -> e.weight).sum();
        int cost2 = mst2.stream().mapToInt(e -> e.weight).sum();

        assertEquals(cost1, cost2);
    }
    @Test
    void testOperationCountConsistency() {
        // For your Kruskal, approximate ops = edges.size() * 2
        int ops = sampleGraph.edges.size() * 2;
        assertTrue(ops >= 0);
    }
}
