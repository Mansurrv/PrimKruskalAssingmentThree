package org.example.algorithms.Prim;

import com.google.gson.Gson;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimAlgorithm {
    static class Edge{
        String from;
        String to;
        int weight;
    }

    static class Graph{
        int id;
        String[] nodes;
        List<Edge> edges;
    }

    static class GraphsWrapper{
        List<Graph> graphs;
    }

    int min(int[] key, Boolean[] set) {
        int min = Integer.MAX_VALUE;
        int min_idx = -1;
        int len = set.length;

        for(int i=0; i<len; i++) {
            if(!set[i] && key[i]<min){
                min = key[i];
                min_idx = i;
            }
        }
        return min_idx;
    }


    void print(int[] parent, int[][] graph, String[] nodes){
        System.out.println("Edge \tWeight");
        int len = graph.length;
        for(int i=0; i<len; i++){
            if(parent[i] != -1) System.out.println(nodes[parent[i]] + " - " + nodes[i] + "\t" + graph[parent[i]][i]);
        }
    }


    void MST(int[][] graph, String[] nodes){
        int V = graph.length;
        int[] parent  = new int[V];
        int[] key     = new int[V];
        Boolean[] set = new Boolean[V];

        for(int i=0; i<V; i++){
            key[i] = Integer.MAX_VALUE;
            set[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for(int count=0; count<V; count++){
            int u  = min(key, set);
            set[u] = true;

            for(int v=0; v<V; v++){
                if(graph[u][v]!=0 && !set[v] && graph[u][v]<key[v]){
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }
        print(parent, graph, nodes);
    }


    static int[][] buildAdjacencyMatrix(String[] nodes, List<Edge> edges){
        int n                       = nodes.length;
        int[][] mtrx                = new int[n][n];
        Map<String,Integer> idx_map = new HashMap<>();
        for(int i=0; i<n; i++){
            idx_map.put(nodes[i], i);
        }

        for(Edge e:edges){
            int from = idx_map.get(e.from);
            int to   = idx_map.get(e.to);
            mtrx[from][to] = e.weight;
            mtrx[to][from] = e.weight;
        }
        return mtrx;
    }


    public static void main(String[] args) {
        try{
            Gson json = new Gson();
            FileReader read = new FileReader("ass_3_input.json");
            GraphsWrapper data = json.fromJson(read, GraphsWrapper.class);
            PrimAlgorithm alg = new PrimAlgorithm();

            for(Graph graph:data.graphs){
                System.out.println(
                        "\nGraph " + graph.id + "MST: "
                );
                int[][] mtrx = buildAdjacencyMatrix(graph.nodes, graph.edges);
                alg.MST(mtrx, graph.nodes);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}