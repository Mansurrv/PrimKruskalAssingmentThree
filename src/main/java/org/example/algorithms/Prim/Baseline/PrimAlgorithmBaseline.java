package org.example.algorithms.Prim.Baseline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class PrimAlgorithmBaseline {

    public PrimResult primOptimized(List<Node> nodes, List<Edge> edges) {
        long startTime = System.nanoTime();

        int V = nodes.size();
        ArrayList<ArrayList<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        Map<Integer, Integer> idToIndex = new HashMap<>();
        for (int i = 0; i < nodes.size(); i++) idToIndex.put(nodes.get(i).id, i);

        for (Edge e : edges) {
            Integer from = idToIndex.get(e.source);
            Integer to = idToIndex.get(e.target);
            if (from == null || to == null) continue;
            adj.get(from).add(new Pair(to, e.weight, from));
            adj.get(to).add(new Pair(from, e.weight, to));
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        boolean[] vis = new boolean[V];
        pq.add(new Pair(0, 0, -1));

        int totalCost = 0;
        int operations = 0;
        List<MSTEdge> mstEdges = new ArrayList<>();

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();
            int node = cur.v;
            int wt = cur.wt;
            int parent = cur.parent;
            operations++;

            if (vis[node]) continue;
            vis[node] = true;
            totalCost += wt;

            if (parent != -1) {
                String from = nodes.get(parent).label != null ? nodes.get(parent).label : String.valueOf(parent);
                String to = nodes.get(node).label != null ? nodes.get(node).label : String.valueOf(node);
                mstEdges.add(new MSTEdge(from, to, wt));
            }

            for (Pair nei : adj.get(node)) {
                if (!vis[nei.v]) {
                    pq.add(new Pair(nei.v, nei.wt, node));
                    operations++;
                }
            }
        }

        long endTime = System.nanoTime();
        double execTimeMs = (endTime - startTime) / 1_000_000.0;

        PrimResult result = new PrimResult();
        result.mst_edges = mstEdges;
        result.total_cost = totalCost;
        result.operations_count = operations;
        result.execution_time_ms = execTimeMs;
        return result;
    }

    public static void main(String[] args) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Output output = new Output();
            PrimAlgorithmBaseline algorithm = new PrimAlgorithmBaseline();

            int[][] ranges = {{1,5},{6,15},{16,25},{26,28}};
            String[] folders = {"small","medium","large","extralarge"};
            String[] prefixes = {"smallGraph_","medium_","large_","extraLarge_"};

            for (int i = 0; i < ranges.length; i++) {
                for (int graphId = ranges[i][0]; graphId <= ranges[i][1]; graphId++) {
                    String path = "graphs/" + folders[i] + "/" + prefixes[i] + graphId + ".json";
                    try (FileReader reader = new FileReader(path)) {
                        Root root = gson.fromJson(reader, Root.class);
                        PrimResult primResult = algorithm.primOptimized(root.graph.nodes, root.graph.edges);

                        InputStats stats = new InputStats();
                        stats.vertices = root.graph.nodes.size();
                        stats.edges = root.graph.edges.size();

                        ResultWrapper wrapper = new ResultWrapper();
                        wrapper.graph_id = graphId;
                        wrapper.input_stats = stats;
                        wrapper.prim_optimized = primResult;

                        output.results.add(wrapper);
                    }
                }
            }

            String outputPath = "results/Prim/primBaseline.json";
            try (FileWriter writer = new FileWriter(outputPath)) {
                gson.toJson(output, writer);
            }

            System.out.println("\nAll results saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
