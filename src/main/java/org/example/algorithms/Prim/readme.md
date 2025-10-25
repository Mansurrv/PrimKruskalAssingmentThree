# Prim's algorithm for minimum spanning tree

Prim's algorithm is a Greedy algorithm like Kruskal's algorithm. This algorithm always starts with a single node and moves through several adjacent nodes in order to explore all of the connected edges along the way.

The algorithm starts with an empty spanning tree. The idea is to maintain two sets of vertices. The first set contains the vertices. The first set contains the vertices already included in the minimum spanning tree, and the other set contains the vertices not yet included. At every step, it considers all the edges that connect the two sets and picks the minimum weight edge from these edges. After picking the edge, it moves the other endpoint of the edge to the set containing minimum spanning tree.

A group of edges that connects two sets of vertices in a graph is called articulation points in graph theory. So, at every step of prim's algorithm, find a cut, pick the minimum weight edge from the cut, and include this vertex in minimum spanning tree set.

## What we do? step-by-step

At first we must determine an arbitrary vertex as the starting vertex of the minimum spanning tree. We pick 0 in the below diagram. And then follow steps 3 to 5 till there are vertices that are not included in the minimum spanning tree. Further, find edges connecting any tree vertex with the fringe vertices and find the minimum among these edges as well. And add the chosen edge to the minimum spanning tree. Since we consider only the edges that connect fringe vertices with the rest, we never get a cycle. Finally return the minimum spanning tree and exit.

### Time complexity: O(V^2)

As we using adjacency matrix, if the input graph is represented using an adjacency list, then the time complexity of Prim's algorithm can be reduced to O((E+V) * logV) with the help of a binary heap.

### Auxiliary space: O(V)

## Optimized implementation using adjacency list representation and priority queue

At first we transform the adjacency matrix into adjacency list using ArrayList. Then we create a pair class to store the vertex and its weight. We sort the list on the basis of lowest weight as well. And then we create priority queue and push the first vertex and its weight in the queue. Then we just traverse through its edges and store the least weight in a variable called ans. At last after all the vertex we return the ans.

