# Kruskal's minimum spanning tree (mst) algorithm

A minimum spanning tree or minimum weight spanning tree for a weighted, connected, undirected graph is a spanning tree which has minimum weight. The weight of a spanning tree is the sum of all edges in the tree. In Kruskal's algorithm, we sort all edges of the given graph in increasing order. Then it keeps on adding new edges and nodes in the minimum spanning tree if the newly added edge doesn't form a cycle. It picks the minimum weighted edge at first and the maximum weighted edge at last. Thus we can say that it makes a locally optimal choice in each step in order to find the optimal solution. Hence this is a Greedy algorithm.

## How we find minimal spanning tree with Kruskal algorithm?

At first we going to sort all the edges in a non-decreasing order of their weight. And then pick the smallest edge. Check if the forms cycle with a spanning tree so far. If the cycle is not formed include it otherwise discard it. We must repeat pick the smallest edge until there are (v-1) edges in the spanning tree.

### Important: 

! Kruskal's algorithm is use disjoint set of data to detect cycles.

### Time complexity of baseline Kruskal's algorithm:

O(E * logE) or O(E * logV)

#### Explanation:

Sorting of edges takes O(E * logE) time. After sorting, we iterate through all edges and apply find-union algorithm. The find-union operations at least takes O(logE) or O(logV) time. So overall the complexity is O(E*logE + E*logV). The value E can be at most O(V^2) so that means O(logE) and O(logV) same. Therefore the overall time complexity is O(E * logE) or O(E * logV).
