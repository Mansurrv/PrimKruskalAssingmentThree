package org.example.algorithms.Kruskal;

import com.google.gson.*;
import org.example.algorithms.Kruskal.KruskalAlgorithm;
import org.example.algorithms.Kruskal.*;

import java.io.*;
import java.util.*;

public class GraphProcessor {

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
                List<Edge> mst = KruskalAlgorithm.kruskalMST(V, edges);
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
                System.err.println("Error reading file: " + filePath);
            }
        }
    }
}
