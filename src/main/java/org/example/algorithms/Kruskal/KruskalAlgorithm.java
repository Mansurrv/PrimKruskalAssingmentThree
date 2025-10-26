package org.example.algorithms.Kruskal;

import org.example.algorithms.Kruskal.Edge;
import java.util.*;

public class KruskalAlgorithm {

    public static List<Edge> kruskalMST(int V, List<Edge> edges) {
        edges.sort(Comparator.comparingInt(e -> e.weight));
        DSU dsu = new DSU(V);
        List<Edge> mst = new ArrayList<>();

        for (Edge e : edges) {
            int u = e.source - 1;
            int v = e.target - 1;
            if (dsu.find(u) != dsu.find(v)) {
                dsu.union(u, v);
                mst.add(e);
                if (mst.size() == V - 1) break;
            }
        }
        return mst;
    }
}
