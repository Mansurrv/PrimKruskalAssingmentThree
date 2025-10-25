package org.example.algorithms.Kruskal;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class KruskalAlgorithm {
    static class DSU {
        int[] parent, rank;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            int rx = find(x);
            int ry = find(y);
            if (rx == ry) return;
            if (rank[rx] < rank[ry]) {
                parent[rx] = ry;
            } else if (rank[ry] < rank[rx]) {
                parent[ry] = rx;
            } else {
                parent[ry] = rx;
                rank[rx]++;
            }
        }
    }

    static class Edge {
        int source;
        int target;
        int weight;
    }

    static class Node {
        int id;
    }

    static class GraphData {
        List<Node> nodes;
        List<Edge> edges;
    }

    static class GraphInput {
        GraphProperties properties;
        GraphData graph;
    }

    static class GraphProperties {
        boolean weighted;
        boolean multigraph;
        boolean directed;
    }

    public static List<Edge> kruskalMST(int V, List<Edge> edges) {
        edges.sort(Comparator.comparingInt(e -> e.weight));
        DSU dsu = new DSU(V);
        List<Edge> mst = new ArrayList<>();

        for (Edge e : edges) {
            int u = e.source - 1;
            int v = e.target - 1;
            if (dsu.find(u) != dsu.find(v)) {
                dsu.union(u, v);
                mst.add(e);
                if (mst.size() == V - 1) break;
            }
        }
        return mst;
    }

    public static void processGraphs(
            String folderPath,
            String prefix,
            int start,
            int end,
            String algorithm,
            Gson gson,
            JsonArray outputArray
    ) {
        for (int i = start; i <= end; i++) {
            String filePath = folderPath + "/" + prefix + i + ".json";

            try (FileReader reader = new FileReader(filePath)) {
                GraphInput graph = gson.fromJson(reader, GraphInput.class);
                int V = graph.graph.nodes.size();
                List<Edge> edges = graph.graph.edges;

                long startTime = System.nanoTime();
                List<Edge> mst = kruskalMST(V, edges);
                long endTime = System.nanoTime();
                double execTime = (endTime - startTime) / 1_000_000.0;

                int totalCost = mst.stream().mapToInt(e -> e.weight).sum();

                JsonObject result = new JsonObject();
                result.addProperty("graph_id", i);

                JsonObject stats = new JsonObject();
                stats.addProperty("vertices", V);
                stats.addProperty("edges", edges.size());
                result.add("input_stats", stats);

                JsonObject algoResult = new JsonObject();
                JsonArray mstEdges = new JsonArray();
                for (Edge e : mst) {
                    JsonObject edgeJson = new JsonObject();
                    edgeJson.addProperty("from", String.valueOf(e.source - 1));
                    edgeJson.addProperty("to", String.valueOf(e.target - 1));
                    edgeJson.addProperty("weight", e.weight);
                    mstEdges.add(edgeJson);
                }

                algoResult.add("mst_edges", mstEdges);
                algoResult.addProperty("total_cost", totalCost);
                algoResult.addProperty("execution_time_ms", execTime);
                algoResult.addProperty("operations_count", edges.size() * 2);
                result.add(algorithm, algoResult);

                outputArray.add(result);

            } catch (IOException e) {
                System.err.println("Error reading file");
            }
        }
    }

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject output = new JsonObject();
        JsonArray resultsArray = new JsonArray();

        String algorithm = "kruskal";

        // Process each graph folder
        processGraphs("graphs/small", "smallGraph_", 1, 5, algorithm, gson, resultsArray);
        processGraphs("graphs/medium", "medium_", 6, 15, algorithm, gson, resultsArray);
        processGraphs("graphs/large", "large_", 16, 25, algorithm, gson, resultsArray);
        processGraphs("graphs/extralarge", "extraLarge_", 26, 28, algorithm, gson, resultsArray);

        output.add("results", resultsArray);

        try (FileWriter writer = new FileWriter("results/Kruskal/result.json")) {
            gson.toJson(output, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Results saved");
    }
}
