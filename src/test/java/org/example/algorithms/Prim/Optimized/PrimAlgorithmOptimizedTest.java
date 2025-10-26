package org.example.algorithms.Prim.Optimized;

import org.example.algorithms.Prim.Optimized.*;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class PrimAlgorithmOptimizedTest {
    private PrimResult createSmallGraphResult() {
        Node n1 = new Node(); n1.id = 1;
        Node n2 = new Node(); n2.id = 2;
        Node n3 = new Node(); n3.id = 3;
        Node n4 = new Node(); n4.id = 4;
        Edge e1 = new Edge(); e1.source = 1; e1.target = 2; e1.weight = 1;
        Edge e2 = new Edge(); e2.source = 2; e2.target = 3; e2.weight = 2;
        Edge e3 = new Edge(); e3.source = 3; e3.target = 4; e3.weight = 3;
        Edge e4 = new Edge(); e4.source = 4; e4.target = 1; e4.weight = 4;
        List<Node> nodes = List.of(n1, n2, n3, n4);
        List<Edge> edges = List.of(e1, e2, e3, e4);
        int[][] matrix = PrimAlgorithmOptimized.buildAdjacencyMatrix(nodes, edges);
        PrimAlgorithmOptimized algorithm = new PrimAlgorithmOptimized();
        return algorithm.MST(matrix, nodes);
    }

    @Test
    void testMSTTotalCost() {
        PrimResult result = createSmallGraphResult();
        int expectedCost = 1 + 2 + 3;
        assertEquals(expectedCost, result.total_cost, "MST total cost should match expected minimal cost");
    }
    @Test
    void testNoCycles() {
        PrimResult result = createSmallGraphResult();
        int vertexCount = 4;
        assertEquals(vertexCount - 1, result.mst_edges.size(), "MST must have V-1 edges, no cycles");
    }
    @Test
    void testMSTConnectsAllVertices() {
        PrimResult result = createSmallGraphResult();
        Set<String> verticesCovered = new HashSet<>();
        for (MSTEdge e : result.mst_edges) {
            verticesCovered.add(e.from);
            verticesCovered.add(e.to);
        }
        assertEquals(4, verticesCovered.size(), "MST must cover all vertices");
    }
    @Test
    void testDisconnectedGraph() {
        Node n1 = new Node(); n1.id = 1;
        Node n2 = new Node(); n2.id = 2;
        Node n3 = new Node(); n3.id = 3;
        Node n4 = new Node(); n4.id = 4;
        Node n5 = new Node(); n5.id = 5;
        Edge e1 = new Edge(); e1.source = 1; e1.target = 2; e1.weight = 1;
        Edge e2 = new Edge(); e2.source = 2; e2.target = 3; e2.weight = 2;
        Edge e3 = new Edge(); e3.source = 3; e3.target = 4; e3.weight = 3;
        List<Node> nodes = List.of(n1, n2, n3, n4, n5);
        List<Edge> edges = List.of(e1, e2, e3);
        int[][] matrix = PrimAlgorithmOptimized.buildAdjacencyMatrix(nodes, edges);
        PrimAlgorithmOptimized algorithm = new PrimAlgorithmOptimized();
        PrimResult result = algorithm.MST(matrix, nodes);
        boolean disconnectedIncluded = result.mst_edges.stream()
                .anyMatch(e -> e.from.equals("5") || e.to.equals("5"));
        assertFalse(disconnectedIncluded, "Disconnected vertices should not appear in MST");
    }
    @Test
    void testExecutionTimeNonNegative() {
        PrimResult result = createSmallGraphResult();
        assertTrue(result.execution_time_ms >= 0, "Execution time must be non-negative");
    }
    @Test
    void testOperationsNonNegative() {
        PrimResult result = createSmallGraphResult();
        assertTrue(result.operations_count >= 0, "Operation count must be non-negative");
    }
    @Test
    void testReproducibility() {
        PrimResult firstRun = createSmallGraphResult();
        PrimResult secondRun = createSmallGraphResult();
        assertEquals(firstRun.total_cost, secondRun.total_cost, "MST total cost must be identical across runs");
        assertEquals(firstRun.mst_edges.size(), secondRun.mst_edges.size(), "Number of MST edges must be identical");
        assertEquals(firstRun.operations_count, secondRun.operations_count, "Operation count should be consistent");
    }
}
