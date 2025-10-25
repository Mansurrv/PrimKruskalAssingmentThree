package org.example.algorithms.Prim;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class PrimAlgorithmTest {
    private PrimAlgorithm.PrimResult createSmallGraphResult() {
        PrimAlgorithm.Node n1 = new PrimAlgorithm.Node(); n1.id = 1;
        PrimAlgorithm.Node n2 = new PrimAlgorithm.Node(); n2.id = 2;
        PrimAlgorithm.Node n3 = new PrimAlgorithm.Node(); n3.id = 3;
        PrimAlgorithm.Node n4 = new PrimAlgorithm.Node(); n4.id = 4;

        PrimAlgorithm.Edge e1 = new PrimAlgorithm.Edge(); e1.source = 1; e1.target = 2; e1.weight = 1;
        PrimAlgorithm.Edge e2 = new PrimAlgorithm.Edge(); e2.source = 2; e2.target = 3; e2.weight = 2;
        PrimAlgorithm.Edge e3 = new PrimAlgorithm.Edge(); e3.source = 3; e3.target = 4; e3.weight = 3;
        PrimAlgorithm.Edge e4 = new PrimAlgorithm.Edge(); e4.source = 4; e4.target = 1; e4.weight = 4;

        List<PrimAlgorithm.Node> nodes = List.of(n1, n2, n3, n4);
        List<PrimAlgorithm.Edge> edges = List.of(e1, e2, e3, e4);

        int[][] matrix = PrimAlgorithm.buildAdjacencyMatrix(nodes, edges);

        PrimAlgorithm algorithm = new PrimAlgorithm();
        return algorithm.MST(matrix, nodes);
    }

    @Test
    void testMSTTotalCost() {
        PrimAlgorithm.PrimResult result = createSmallGraphResult();
        int expectedCost = 1 + 2 + 3; // minimal edges
        assertEquals(expectedCost, result.total_cost, "MST total cost should match expected minimal cost");
    }
    @Test
    void testNoCycles() {
        PrimAlgorithm.PrimResult result = createSmallGraphResult();
        int vertexCount = 4; // nodes in graph
        assertEquals(vertexCount - 1, result.mst_edges.size(), "MST must have V-1 edges, no cycles");
    }
    @Test
    void testMSTConnectsAllVertices() {
        PrimAlgorithm.PrimResult result = createSmallGraphResult();
        Set<String> verticesCovered = new HashSet<>();
        for (PrimAlgorithm.MSTEdge e : result.mst_edges) {
            verticesCovered.add(e.from);
            verticesCovered.add(e.to);
        }
        assertEquals(4, verticesCovered.size(), "MST must cover all vertices");
    }
    @Test
    void testDisconnectedGraph() {
        PrimAlgorithm.Node n1 = new PrimAlgorithm.Node(); n1.id = 1;
        PrimAlgorithm.Node n2 = new PrimAlgorithm.Node(); n2.id = 2;
        PrimAlgorithm.Node n3 = new PrimAlgorithm.Node(); n3.id = 3;
        PrimAlgorithm.Node n4 = new PrimAlgorithm.Node(); n4.id = 4;
        PrimAlgorithm.Node n5 = new PrimAlgorithm.Node(); n5.id = 5;

        PrimAlgorithm.Edge e1 = new PrimAlgorithm.Edge(); e1.source = 1; e1.target = 2; e1.weight = 1;
        PrimAlgorithm.Edge e2 = new PrimAlgorithm.Edge(); e2.source = 2; e2.target = 3; e2.weight = 2;
        PrimAlgorithm.Edge e3 = new PrimAlgorithm.Edge(); e3.source = 3; e3.target = 4; e3.weight = 3;

        List<PrimAlgorithm.Node> nodes = List.of(n1, n2, n3, n4, n5);
        List<PrimAlgorithm.Edge> edges = List.of(e1, e2, e3);

        int[][] matrix = PrimAlgorithm.buildAdjacencyMatrix(nodes, edges);
        PrimAlgorithm algorithm = new PrimAlgorithm();
        PrimAlgorithm.PrimResult result = algorithm.MST(matrix, nodes);

        boolean disconnectedIncluded = result.mst_edges.stream()
                .anyMatch(e -> e.from.equals("5") || e.to.equals("5"));
        assertFalse(disconnectedIncluded, "Disconnected vertices should not appear in MST");
    }
    @Test
    void testExecutionTime() {
        PrimAlgorithm.PrimResult result = createSmallGraphResult();
        assertTrue(result.execution_time_ms >= 0, "Execution time must be non-negative");
    }


    @Test
    void testExecutionTimeNonNegative() {
        PrimAlgorithm.PrimResult result = createSmallGraphResult();
        assertTrue(result.execution_time_ms >= 0, "Execution time must be non-negative");
    }
    @Test
    void testOperationsNonNegative() {
        PrimAlgorithm.PrimResult result = createSmallGraphResult();
        assertTrue(result.operations_count >= 0, "Operation count must be non-negative");
    }
    @Test
    void testReproducibility() {
        PrimAlgorithm.PrimResult firstRun = createSmallGraphResult();
        PrimAlgorithm.PrimResult secondRun = createSmallGraphResult();

        assertEquals(firstRun.total_cost, secondRun.total_cost, "MST total cost must be identical across runs");
        assertEquals(firstRun.mst_edges.size(), secondRun.mst_edges.size(), "Number of MST edges must be identical");
        assertEquals(firstRun.operations_count, secondRun.operations_count, "Operation count should be consistent");
    }
}
