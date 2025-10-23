package org.example.algorithms.Prim;

public class PrimAlgorithm {
    int minKey(int key[], Boolean mstSet[]){
        int min = Integer.MAX_VALUE;
        int min_idx = -1;
        int len=mstSet.length;

        for (int v=0; v<len; v++){
            if (mstSet[v]==false && key[v]<min){
                min=key[v];
                min_idx=v;
            }
        }
        return min_idx;
    }

    void primMst(int parent[], int graph[][]){
        System.out.println("Edge \tWeight");
        int len=graph.length;
        for (int i=1; i<len; i++){
            System.out.println(parent[i] + " - " + i + "\t" + graph[parent[i]][i]);
        }
    }
}
