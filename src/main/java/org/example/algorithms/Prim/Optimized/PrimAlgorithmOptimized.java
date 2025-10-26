package org.example.algorithms.Prim.Optimized;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import org.example.algorithms.Prim.Optimized.*;

public class PrimAlgorithmOptimized {
    int min(int[] key, Boolean[] set, int[] operations) {
        int min = Integer.MAX_VALUE;
        int min_idx = -1;
        int len = set.length;

        for (int i = 0; i < len; i++) {
            operations[0]++;
            if (!set[i] && key[i] < min) {
                min = key[i];
                min_idx = i;
            }
        }
        return min_idx;
    }

    PrimResult MST(int[][] graph, List<Node> nodes) {
        long startTime = System.nanoTime();

        int V = graph.length;
        int[] parent = new int[V];
        int[] key = new int[V];
        Boolean[] set = new Boolean[V];
        int[] operations = {0};

        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            set[i] = false;
            operations[0]++;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < V - 1; count++) {
            int u = min(key, set, operations);
            set[u] = true;

            for (int v = 0; v < V; v++) {
                operations[0]++;
                if (graph[u][v] != 0 && !set[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        int totalCost = 0;
        List<MSTEdge> mstEdges = new ArrayList<>();
        for (int i = 1; i < V; i++) {
            if (parent[i] != -1) {
                int weight = graph[parent[i]][i];
                totalCost += weight;

                String from = nodes.get(parent[i]).label != null ? nodes.get(parent[i]).label : String.valueOf(parent[i]);
                String to = nodes.get(i).label != null ? nodes.get(i).label : String.valueOf(i);

                mstEdges.add(new MSTEdge(from, to, weight));
            }
        }

        long endTime = System.nanoTime();
        double execTimeMs = (endTime - startTime) / 1_000_000.0;

        PrimResult result = new PrimResult();
        result.mst_edges = mstEdges;
        result.total_cost = totalCost;
        result.operations_count = operations[0];
        result.execution_time_ms = execTimeMs;

        return result;
    }

    static int[][] buildAdjacencyMatrix(List<Node> nodes, List<Edge> edges) {
        int n = nodes.size();
        int[][] matrix = new int[n][n];
        Map<Integer, Integer> indexMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            indexMap.put(nodes.get(i).id, i);
        }

        for (Edge e : edges) {
            int from = indexMap.get(e.source);
            int to = indexMap.get(e.target);
            matrix[from][to] = e.weight;
            matrix[to][from] = e.weight;
        }

        return matrix;
    }

    static void processGraphs(String folderPath, String filePrefix, int startId, int endId,
                              PrimAlgorithmOptimized algorithm, Gson gson, Output output) {
        for (int graphId = startId; graphId <= endId; graphId++) {
            try {
                String path = folderPath + "/" + filePrefix + "_" + graphId + ".json";
                FileReader reader = new FileReader(path);
                Root root = gson.fromJson(reader, Root.class);

                int[][] matrix = buildAdjacencyMatrix(root.graph.nodes, root.graph.edges);
                PrimResult primResult = algorithm.MST(matrix, root.graph.nodes);

                InputStats stats = new InputStats();
                stats.vertices = root.graph.nodes.size();
                stats.edges = root.graph.edges.size();

                ResultWrapper wrapper = new ResultWrapper();
                wrapper.graph_id = graphId;
                wrapper.input_stats = stats;
                wrapper.prim = primResult;

                output.results.add(wrapper);
                reader.close();

            } catch (Exception e) {
                System.err.println("Failed to read or process");
            }
        }
    }

    public static void main(String[] args) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Output output = new Output();
            PrimAlgorithmOptimized algorithm = new PrimAlgorithmOptimized();

            processGraphs("graphs/small", "smallGraph", 1, 5, algorithm, gson, output);
            processGraphs("graphs/medium", "medium", 6, 15, algorithm, gson, output);
            processGraphs("graphs/large", "large", 16, 25, algorithm, gson, output);
            processGraphs("graphs/extralarge", "extraLarge", 26, 28, algorithm, gson, output);

            File resultsDir = new File("results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }

            String outputPath = "results/Prim/primOptimized.json";
            FileWriter writer = new FileWriter(outputPath);
            gson.toJson(output, writer);
            writer.close();

            System.out.println("Results saved");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
