package org.example.algorithms.Kruskal;

import com.google.gson.*;
import org.example.algorithms.Kruskal.GraphProcessor;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject output = new JsonObject();
        JsonArray resultsArray = new JsonArray();

        String algorithm = "kruskal";

        GraphProcessor.processGraphs("graphs/small", "smallGraph_", 1, 5, algorithm, gson, resultsArray);
        GraphProcessor.processGraphs("graphs/medium", "medium_", 6, 15, algorithm, gson, resultsArray);
        GraphProcessor.processGraphs("graphs/large", "large_", 16, 25, algorithm, gson, resultsArray);
        GraphProcessor.processGraphs("graphs/extralarge", "extraLarge_", 26, 28, algorithm, gson, resultsArray);

        output.add("results", resultsArray);

        try (FileWriter writer = new FileWriter("results/Kruskal/result.json")) {
            gson.toJson(output, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Results saved successfully.");
    }
}
