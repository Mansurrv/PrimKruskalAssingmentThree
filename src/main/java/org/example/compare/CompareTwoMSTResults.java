package org.example.compare;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import java.io.*;
import java.util.*;

public class CompareTwoMSTResults {
    public static void main(String[] args) {
        String kruskalPath = "results/Kruskal/result.json";
        String primPath = "results/Prim/primBaseline.json";
        String primOptPath = "results/Prim/primOptimized.json";
        String outputCsv = "compareResult/comparison_results.csv";

        Gson gson = new Gson();

        try {
            new File("compareResult").mkdirs();

            List<GraphResult> kruskalList = readJson(kruskalPath, gson);
            List<GraphResult> primList = readJson(primPath, gson);
            List<GraphResult> primOptList = readJson(primOptPath, gson);

            System.out.printf("Loaded: Kruskal=%d, Prim=%d, PrimOpt=%d%n",
                    kruskalList.size(), primList.size(), primOptList.size());

            Map<Integer, GraphResult> kruskalMap = toMap(kruskalList);
            Map<Integer, GraphResult> primMap = toMap(primList);
            Map<Integer, GraphResult> primOptMap = toMap(primOptList);

            try (PrintWriter writer = new PrintWriter(new FileWriter(outputCsv))) {
                writer.println("Graph_ID,Vertices,Edges,"
                        + "Kruskal_Cost,Prim_Cost,PrimOpt_Cost,All_Costs_Match,"
                        + "Kruskal_Edges,Prim_Edges,PrimOpt_Edges,Edges_OK,"
                        + "Kruskal_Time_ms,Prim_Time_ms,PrimOpt_Time_ms,"
                        + "Kruskal_Ops,Prim_Ops,PrimOpt_Ops");

                for (int id : kruskalMap.keySet()) {
                    GraphResult k = kruskalMap.get(id);
                    GraphResult p = primMap.get(id);
                    GraphResult po = primOptMap.get(id);
                    MSTData kr = k.kruskal;
                    MSTData pr = (p.prim != null) ? p.prim : p.primOptimized;
                    MSTData pro = (po.primOptimized != null) ? po.primOptimized : po.prim;

                    boolean costMatch = Math.abs(kr.totalCost - pr.totalCost) < 1e-6 &&
                            Math.abs(kr.totalCost - pro.totalCost) < 1e-6;
                    boolean edgesOK = (kr.mstEdges.size() == k.inputStats.vertices - 1) &&
                            (pr.mstEdges.size() == p.inputStats.vertices - 1) &&
                            (pro.mstEdges.size() == po.inputStats.vertices - 1);

                    writer.printf(Locale.US,
                            "%d,%d,%d,%.2f,%.2f,%.2f,%s,%d,%d,%d,%s,%.3f,%.3f,%.3f,%d,%d,%d%n",
                            id,
                            k.inputStats.vertices,
                            k.inputStats.edges,
                            kr.totalCost,
                            pr.totalCost,
                            pro.totalCost,
                            costMatch ? "YES" : "NO",
                            kr.mstEdges.size(),
                            pr.mstEdges.size(),
                            pro.mstEdges.size(),
                            edgesOK ? "YES" : "NO",
                            kr.executionTime,
                            pr.executionTime,
                            pro.executionTime,
                            kr.operationsCount,
                            pr.operationsCount,
                            pro.operationsCount
                    );
                }

            }

            System.out.println("Results are saved");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<Integer, GraphResult> toMap(List<GraphResult> list) {
        Map<Integer, GraphResult> map = new HashMap<>();
        for (GraphResult g : list) map.put(g.graphId, g);
        return map;
    }

    private static List<GraphResult> readJson(String path, Gson gson) throws IOException {
        try (Reader reader = new FileReader(path)) {
            Root root = gson.fromJson(reader, Root.class);
            return (root != null && root.results != null) ? root.results : Collections.emptyList();
        }
    }
}
