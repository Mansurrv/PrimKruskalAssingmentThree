package org.example.compare;

import com.google.gson.annotations.SerializedName;

public class GraphResult {
    @SerializedName("graph_id")
    int graphId;
    @SerializedName("input_stats")
    InputStats inputStats;
    MSTData kruskal;
    MSTData prim;
    @SerializedName("prim_optimized")
    MSTData primOptimized;
}