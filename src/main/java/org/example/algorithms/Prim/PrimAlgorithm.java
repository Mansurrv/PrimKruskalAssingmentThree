package org.example.algorithms.Prim;

import com.google.gson.Gson;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimAlgorithm {
    static class Node {
        int id;
    }

    static class Edge {
        int source;
        int target;
        int weight;
    }

    static class GraphData {
        List<Node> nodes;
        List<Edge> edges;
    }

    static class Root {
        Map<String, Object> properties;
        GraphData graph;
    }

    int min(int[] key, Boolean[] set) {
        int min = Integer.MAX_VALUE;
        int min_idx = -1;
        int len = set.length;

        for (int i = 0; i < len; i++) {
            if (!set[i] && key[i] < min) {
                min = key[i];
                min_idx = i;
            }
        }
        return min_idx;
    }

    void print(int[] parent, int[][] graph) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < graph.length; i++) {
            if (parent[i] != -1)
                System.out.println(parent[i] + " - " + i + "\t" + graph[parent[i]][i]);
        }
    }

    void MST(int[][] graph) {
        int V = graph.length;
        int[] parent = new int[V];
        int[] key = new int[V];
        Boolean[] set = new Boolean[V];

        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            set[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < V - 1; count++) {
            int u = min(key, set);
            set[u] = true;

            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !set[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }
        print(parent, graph);
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

    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("graphs/small/smallGraph_1.json");
            Root root = gson.fromJson(reader, Root.class);

            PrimAlgorithm algorithm = new PrimAlgorithm();
            System.out.println("Loaded graph with " + root.graph.nodes.size() + " nodes and " + root.graph.edges.size() + " edges\n");

            System.out.println("Input edges:");
            for (Edge e : root.graph.edges) {
                System.out.println(e.source + " - " + e.target + " : " + e.weight);
            }

            System.out.println("\nMinimum Spanning Tree:");
            int[][] matrix = buildAdjacencyMatrix(root.graph.nodes, root.graph.edges);
            algorithm.MST(matrix);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
