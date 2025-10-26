package org.example.algorithms.Prim.Baseline;

public class Pair implements Comparable<Pair> {
    public int v, wt, parent;

    public Pair(int v, int wt, int parent) {
        this.v = v;
        this.wt = wt;
        this.parent = parent;
    }

    @Override
    public int compareTo(Pair that) {
        return this.wt - that.wt;
    }
}
