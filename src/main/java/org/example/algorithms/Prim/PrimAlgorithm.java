package org.example.algorithms.Prim;

public class PrimAlgorithm {
    int minKey(int key[], Boolean set[]){
        int min = Integer.MAX_VALUE;
        int min_idx = -1;
        int len = set.length;

        for(int i=0; i<len; i++){
            if (set[i]==false && key[i]<min){
                min = key[i];
                min_idx = i;
            }
        }
        return min_idx;
    }


    void print(int parent[], int graph[][]){
        System.out.println("Edge \tWeight");
        for(int i=1; i<graph.length; i++){
            System.out.println(parent[i] + " - " + i + "\t" + graph[parent[i]][i]);
        }
    }


    void prim(int graph[][]){
        int V = graph.length;
        int parent[] = new int[V];
        int key[] = new int[V];
        Boolean set[] = new Boolean[V];

        for(int i=0; i<V; i++){
            key[i] = Integer.MAX_VALUE;
            set[i] = false;
        }

        key[0]=0;
        parent[0]=-1;

        for(int count=0; count<V-1; count++){
            int u = minKey(key,set);
            set[u] = true;
            for(int v=0; v<V; v++){
                if(graph[u][v]!=0 && set[v]==false && graph[u][v]<key[v]){
                    parent[v]=u;
                    key[v]=graph[u][v];
                }
            }
        }
        print(parent, graph);
    }


    public static  void main(String[] args) {
        PrimAlgorithm algorithm = new PrimAlgorithm();
        int graph[][] = new int[][] {
                { 0, 2, 0, 6, 0 },
                { 2, 0, 3, 8, 5 },
                { 0, 3, 0, 0, 7 },
                { 6, 8, 0, 0, 9 },
                { 0, 5, 7, 9, 0 }
        };
        algorithm.prim(graph);
    }
}