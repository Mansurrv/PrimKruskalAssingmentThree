package org.example.algorithms.Prim;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class PrimAlgorithmBaselineTest {
    private PrimAlgorithmBaseline.PrimResult createSmallGraphResult() {
        PrimAlgorithmBaseline.Node n1 = new PrimAlgorithmBaseline.Node(); n1.id = 0;
        PrimAlgorithmBaseline.Node n2 = new PrimAlgorithmBaseline.Node(); n2.id = 1;
        PrimAlgorithmBaseline.Node n3 = new PrimAlgorithmBaseline.Node(); n3.id = 2;
        PrimAlgorithmBaseline.Node n4 = new PrimAlgorithmBaseline.Node(); n4.id = 3;
        PrimAlgorithmBaseline.Edge e1 = new PrimAlgorithmBaseline.Edge(); e1.source = 0; e1.target = 1; e1.weight = 1;
        PrimAlgorithmBaseline.Edge e2 = new PrimAlgorithmBaseline.Edge(); e2.source = 1; e2.target = 2; e2.weight = 2;
        PrimAlgorithmBaseline.Edge e3 = new PrimAlgorithmBaseline.Edge(); e3.source = 2; e3.target = 3; e3.weight = 3;
        PrimAlgorithmBaseline.Edge e4 = new PrimAlgorithmBaseline.Edge(); e4.source = 3; e4.target = 0; e4.weight = 4;
        List<PrimAlgorithmBaseline.Node> nodes = List.of(n1, n2, n3, n4);
        List<PrimAlgorithmBaseline.Edge> edges = List.of(e1, e2, e3, e4);
        PrimAlgorithmBaseline algorithm = new PrimAlgorithmBaseline();
        return algorithm.primOptimized(nodes, edges);
    }


    @Test
    void testMSTTotalCost() {
        PrimAlgorithmBaseline.PrimResult result = createSmallGraphResult();
        int expectedCost = 1 + 2 + 3;
        assertEquals(expectedCost, result.total_cost);
    }
    @Test
    void testNoCycles() {
        PrimAlgorithmBaseline.PrimResult result = createSmallGraphResult();
        int vertexCount = 4;
        assertEquals(vertexCount - 1, result.mst_edges.size());
    }
    @Test
    void testMSTConnectsAllVertices() {
        PrimAlgorithmBaseline.PrimResult result = createSmallGraphResult();
        Set<String> verticesCovered = new HashSet<>();
        for (PrimAlgorithmBaseline.MSTEdge e : result.mst_edges) {
            verticesCovered.add(e.from);
            verticesCovered.add(e.to);
        }
        assertEquals(4, verticesCovered.size());
    }
    @Test
    void testDisconnectedGraph() {
        PrimAlgorithmBaseline.Node n1 = new PrimAlgorithmBaseline.Node(); n1.id = 0;
        PrimAlgorithmBaseline.Node n2 = new PrimAlgorithmBaseline.Node(); n2.id = 1;
        PrimAlgorithmBaseline.Node n3 = new PrimAlgorithmBaseline.Node(); n3.id = 2;
        PrimAlgorithmBaseline.Node n4 = new PrimAlgorithmBaseline.Node(); n4.id = 3;
        PrimAlgorithmBaseline.Node n5 = new PrimAlgorithmBaseline.Node(); n5.id = 4;
        PrimAlgorithmBaseline.Edge e1 = new PrimAlgorithmBaseline.Edge(); e1.source = 0; e1.target = 1; e1.weight = 1;
        PrimAlgorithmBaseline.Edge e2 = new PrimAlgorithmBaseline.Edge(); e2.source = 1; e2.target = 2; e2.weight = 2;
        PrimAlgorithmBaseline.Edge e3 = new PrimAlgorithmBaseline.Edge(); e3.source = 2; e3.target = 3; e3.weight = 3;
        List<PrimAlgorithmBaseline.Node> nodes = List.of(n1, n2, n3, n4, n5);
        List<PrimAlgorithmBaseline.Edge> edges = List.of(e1, e2, e3);
        PrimAlgorithmBaseline algorithm = new PrimAlgorithmBaseline();
        PrimAlgorithmBaseline.PrimResult result = algorithm.primOptimized(nodes, edges);
        boolean disconnectedIncluded = result.mst_edges.stream()
                .anyMatch(e -> e.from.equals("4") || e.to.equals("4"));
        assertFalse(disconnectedIncluded);
    }
    @Test
    void testExecutionTimeNonNegative() {
        PrimAlgorithmBaseline.PrimResult result = createSmallGraphResult();
        assertTrue(result.execution_time_ms >= 0);
    }
    @Test
    void testOperationsNonNegative() {
        PrimAlgorithmBaseline.PrimResult result = createSmallGraphResult();
        assertTrue(result.operations_count >= 0);
    }

    @Test
    void testReproducibility() {
        PrimAlgorithmBaseline.PrimResult firstRun = createSmallGraphResult();
        PrimAlgorithmBaseline.PrimResult secondRun = createSmallGraphResult();

        assertEquals(firstRun.total_cost, secondRun.total_cost);
        assertEquals(firstRun.mst_edges.size(), secondRun.mst_edges.size());
        assertEquals(firstRun.operations_count, secondRun.operations_count);
    }
}
