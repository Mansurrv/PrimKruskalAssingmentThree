package org.example.algorithms.Prim.Baseline;

public class MSTEdge {
    String from;
    String to;
    int weight;

    MSTEdge(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
