package org.example.visualize.Small;

import com.google.gson.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.io.FileReader;

public class Small {
    public static void main(String[] args) throws Exception {
        System.setProperty("org.graphstream.ui", "swing");
        int numberOfGraphs = 5;
        for (int i = 1; i <= numberOfGraphs; i++) {
            String filePath = "graphs/small/smallGraph_" + i + ".json";
            visualizeGraph(filePath, "🟠 Small Graph No. " + i);
        }
    }

    public static void visualizeGraph(String filePath, String title) throws Exception {
        Gson gson = new Gson();
        JsonObject data = gson.fromJson(new FileReader(filePath), JsonObject.class);
        JsonObject graphObj = data.getAsJsonObject("graph");
        JsonArray nodes = graphObj.getAsJsonArray("nodes");
        JsonArray edges = graphObj.getAsJsonArray("edges");
        Graph graph = new SingleGraph(title);

        for (JsonElement nodeEl : nodes) {
            JsonObject node = nodeEl.getAsJsonObject();
            String id = String.valueOf(node.get("id").getAsInt());
            if (graph.getNode(id) == null) {
                graph.addNode(id).setAttribute("ui.label", id);
            }
        }

        for (JsonElement edgeEl : edges) {
            JsonObject edge = edgeEl.getAsJsonObject();
            String source = String.valueOf(edge.get("source").getAsInt());
            String target = String.valueOf(edge.get("target").getAsInt());
            int weight = edge.get("weight").getAsInt();
            String edgeId = source + "-" + target;
            if (graph.getEdge(edgeId) == null && graph.getEdge(target + "-" + source) == null) {
                Edge e = graph.addEdge(edgeId, source, target);
                if (e != null) {
                    e.setAttribute("weight", weight);
                    e.setAttribute("ui.label", String.valueOf(weight));
                }
            }
        }

        graph.setAttribute("ui.stylesheet",
                "node {" +
                        " fill-color: orange;" +
                        " size: 20px;" +
                        " text-size: 14px;" +
                        " text-alignment: above;" +
                        "}" +
                        "edge {" +
                        " fill-color: gray;" +
                        " text-size: 12px;" +
                        " text-alignment: above;" +
                        "}");

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        ViewPanel viewPanel = viewer.addDefaultView(false);

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(viewPanel);
        frame.setSize(800, 600);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
