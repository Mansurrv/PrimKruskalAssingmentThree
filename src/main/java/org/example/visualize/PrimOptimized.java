package org.example.visualize;

import com.google.gson.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.io.FileReader;

public class PrimOptimized {
    public static void main(String[] args) throws Exception {
        System.setProperty("org.graphstream.ui", "swing");
        String filePath = "results/Prim/primOptimized.json";

        Gson gson = new Gson();
        JsonObject data = gson.fromJson(new FileReader(filePath), JsonObject.class);

        JsonObject firstGraph = data.getAsJsonArray("results").get(0).getAsJsonObject();
        JsonObject prim = firstGraph.getAsJsonObject("prim");
        JsonArray mstEdges = prim.getAsJsonArray("mst_edges");

        Graph graph = new SingleGraph("MST Visualization");

        for (JsonElement edgeEl : mstEdges) {
            JsonObject edge = edgeEl.getAsJsonObject();
            String from = edge.get("from").getAsString();
            String to = edge.get("to").getAsString();
            int weight = edge.get("weight").getAsInt();

            if (graph.getNode(from) == null)
                graph.addNode(from);
            if (graph.getNode(to) == null)
                graph.addNode(to);

            String edgeId = from + "-" + to;
            if (graph.getEdge(edgeId) == null)
                graph.addEdge(edgeId, from, to).setAttribute("weight", weight);
        }

        graph.setAttribute("ui.stylesheet",
                "node { fill-color: orange; size: 20px; text-size: 14px; }" +
                        "edge { fill-color: gray; text-size: 14px; }");

        for (Node node : graph) {
            node.setAttribute("ui.label", node.getId());
        }

        for (Edge edge : graph.getEachEdge()) {
            edge.setAttribute("ui.label", "" + edge.getAttribute("weight"));
        }

        graph.display();
    }
}
