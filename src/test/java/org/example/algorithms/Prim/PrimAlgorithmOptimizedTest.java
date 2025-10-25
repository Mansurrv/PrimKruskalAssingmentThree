package org.example.algorithms.Prim;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class PrimAlgorithmOptimizedTest {
    private PrimAlgorithmOptimized.PrimResult createSmallGraphResult() {
        PrimAlgorithmOptimized.Node n1 = new PrimAlgorithmOptimized.Node(); n1.id = 1;
        PrimAlgorithmOptimized.Node n2 = new PrimAlgorithmOptimized.Node(); n2.id = 2;
        PrimAlgorithmOptimized.Node n3 = new PrimAlgorithmOptimized.Node(); n3.id = 3;
        PrimAlgorithmOptimized.Node n4 = new PrimAlgorithmOptimized.Node(); n4.id = 4;

        PrimAlgorithmOptimized.Edge e1 = new PrimAlgorithmOptimized.Edge(); e1.source = 1; e1.target = 2; e1.weight = 1;
        PrimAlgorithmOptimized.Edge e2 = new PrimAlgorithmOptimized.Edge(); e2.source = 2; e2.target = 3; e2.weight = 2;
        PrimAlgorithmOptimized.Edge e3 = new PrimAlgorithmOptimized.Edge(); e3.source = 3; e3.target = 4; e3.weight = 3;
        PrimAlgorithmOptimized.Edge e4 = new PrimAlgorithmOptimized.Edge(); e4.source = 4; e4.target = 1; e4.weight = 4;

        List<PrimAlgorithmOptimized.Node> nodes = List.of(n1, n2, n3, n4);
        List<PrimAlgorithmOptimized.Edge> edges = List.of(e1, e2, e3, e4);

        int[][] matrix = PrimAlgorithmOptimized.buildAdjacencyMatrix(nodes, edges);

        PrimAlgorithmOptimized algorithm = new PrimAlgorithmOptimized();
        return algorithm.MST(matrix, nodes);
    }

    @Test
    void testMSTTotalCost() {
        PrimAlgorithmOptimized.PrimResult result = createSmallGraphResult();
        int expectedCost = 1 + 2 + 3; // minimal edges
        assertEquals(expectedCost, result.total_cost, "MST total cost should match expected minimal cost");
    }
    @Test
    void testNoCycles() {
        PrimAlgorithmOptimized.PrimResult result = createSmallGraphResult();
        int vertexCount = 4; // nodes in graph
        assertEquals(vertexCount - 1, result.mst_edges.size(), "MST must have V-1 edges, no cycles");
    }
    @Test
    void testMSTConnectsAllVertices() {
        PrimAlgorithmOptimized.PrimResult result = createSmallGraphResult();
        Set<String> verticesCovered = new HashSet<>();
        for (PrimAlgorithmOptimized.MSTEdge e : result.mst_edges) {
            verticesCovered.add(e.from);
            verticesCovered.add(e.to);
        }
        assertEquals(4, verticesCovered.size(), "MST must cover all vertices");
    }
    @Test
    void testDisconnectedGraph() {
        PrimAlgorithmOptimized.Node n1 = new PrimAlgorithmOptimized.Node(); n1.id = 1;
        PrimAlgorithmOptimized.Node n2 = new PrimAlgorithmOptimized.Node(); n2.id = 2;
        PrimAlgorithmOptimized.Node n3 = new PrimAlgorithmOptimized.Node(); n3.id = 3;
        PrimAlgorithmOptimized.Node n4 = new PrimAlgorithmOptimized.Node(); n4.id = 4;
        PrimAlgorithmOptimized.Node n5 = new PrimAlgorithmOptimized.Node(); n5.id = 5;

        PrimAlgorithmOptimized.Edge e1 = new PrimAlgorithmOptimized.Edge(); e1.source = 1; e1.target = 2; e1.weight = 1;
        PrimAlgorithmOptimized.Edge e2 = new PrimAlgorithmOptimized.Edge(); e2.source = 2; e2.target = 3; e2.weight = 2;
        PrimAlgorithmOptimized.Edge e3 = new PrimAlgorithmOptimized.Edge(); e3.source = 3; e3.target = 4; e3.weight = 3;

        List<PrimAlgorithmOptimized.Node> nodes = List.of(n1, n2, n3, n4, n5);
        List<PrimAlgorithmOptimized.Edge> edges = List.of(e1, e2, e3);

        int[][] matrix = PrimAlgorithmOptimized.buildAdjacencyMatrix(nodes, edges);
        PrimAlgorithmOptimized algorithm = new PrimAlgorithmOptimized();
        PrimAlgorithmOptimized.PrimResult result = algorithm.MST(matrix, nodes);

        boolean disconnectedIncluded = result.mst_edges.stream()
                .anyMatch(e -> e.from.equals("5") || e.to.equals("5"));
        assertFalse(disconnectedIncluded, "Disconnected vertices should not appear in MST");
    }
    @Test
    void testExecutionTime() {
        PrimAlgorithmOptimized.PrimResult result = createSmallGraphResult();
        assertTrue(result.execution_time_ms >= 0, "Execution time must be non-negative");
    }


    @Test
    void testExecutionTimeNonNegative() {
        PrimAlgorithmOptimized.PrimResult result = createSmallGraphResult();
        assertTrue(result.execution_time_ms >= 0, "Execution time must be non-negative");
    }
    @Test
    void testOperationsNonNegative() {
        PrimAlgorithmOptimized.PrimResult result = createSmallGraphResult();
        assertTrue(result.operations_count >= 0, "Operation count must be non-negative");
    }
    @Test
    void testReproducibility() {
        PrimAlgorithmOptimized.PrimResult firstRun = createSmallGraphResult();
        PrimAlgorithmOptimized.PrimResult secondRun = createSmallGraphResult();

        assertEquals(firstRun.total_cost, secondRun.total_cost, "MST total cost must be identical across runs");
        assertEquals(firstRun.mst_edges.size(), secondRun.mst_edges.size(), "Number of MST edges must be identical");
        assertEquals(firstRun.operations_count, secondRun.operations_count, "Operation count should be consistent");
    }
}
