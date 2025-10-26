package org.example.algorithms.Prim.Optimized;

public class MSTEdge {
    public String from;
    public String to;
    public int weight;

    MSTEdge(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
