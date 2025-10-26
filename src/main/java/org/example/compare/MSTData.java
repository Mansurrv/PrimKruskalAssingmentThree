package org.example.compare;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MSTData {
    @SerializedName("mst_edges")
    List<MSTEdge> mstEdges;
    @SerializedName("total_cost")
    double totalCost;
    @SerializedName("execution_time_ms")
    double executionTime;
    @SerializedName("operations_count")
    int operationsCount;
}