package org.example.algorithms.Kruskal;

import java.util.Arrays;
import java.util.Comparator;

public class KruskalAlgorithm {
    public static int kruskalMST(int V, int[][] edges){
        Arrays.sort(edges, Comparator.comparingInt(e -> e[2]));
        DSU dsu = new DSU(V);

        int cost=0;
        int count=0;

        for(int[] e:edges){
            int x=e[0];
            int y=e[1];
            int w=e[2];

            if(dsu.find(x)!=dsu.find(y)){
                dsu.union(x,y);
                cost+=w;
                if(++count == V-1) break;
            }
        }
        return cost;
    }
    public void main(String[] args){
        int[][] edges = {
                {0,1,10},
                {1,3,15},
                {2,3,4},
                {2,0,6},
                {0,3,5}
        };
        System.out.println(kruskalMST(4,edges));
    }
}


class DSU{
    private int[] parent;
    private int[] rank;

    public DSU(int n){
        parent = new int[n];
        rank = new int[n];
        for(int i=0; i<n; i++){
            parent[i] = i;
            rank[i] = 1;
        }
    }
    public int find(int n){
        if(parent[n] != n){
            parent[n] = find(parent[n]);
        }
        return parent[n];
    }
    public void union(int x, int y){
        int findx = find(x);
        int findy = find(y);
        if (findx!=findy){
            if(rank[findx]<rank[findy]){
                parent[findx] = findy;
            }else if(rank[findy]<rank[findx]){
                parent[findy] = findx;
            }else{
                parent[findy] =  findx;
                rank[findx]++;
            }
        }
    }
}
