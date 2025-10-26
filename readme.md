# Computitional Complexity and Big - O notation

Computational complexity is a branch that focuses on a classifying computational process. According their inherit difficulty across their different dimensions like as a function input size, as a function how much resources will be used, on average in the best case or in the worst case. In other words, we would like to infer how much resources the algorithm uses just from its specification, its called algorithm of analysis.

## Prim's algorithm

Prim's algorithm is a key player in finding a minimum spanning tree. It starts with a single vertex. Then grows the tree by adding edge to a connected tree to a new vertex. This greedy approach ensures a optimal solution.

Understanding prim's algorithm is a crucial for tackling network design and optimizing problems. Its implementation involves clever use of data structures like priority queues, making it an excellent example of algorithm  design and efficiency consideration.

## Kruskal's algorithm

Kruskal's algorithm builds the minimum spanning tree by selecting edges in order of increasing weight, regardless of their connectivity to the existing tree. Like prim's algorithm the Kruskal's algorithm use greedy approaches to construct a minimum spanning tree for a weighted, undirected graph. This algorithm employs a disjoint-set data structure to efficiently check for cycles. Kruskal's algorithm considers all edges the graph during its execution.

### Edge selection and tree grows

At first it uses cut property ensuring minimum weight edge crossing any cut must be in minimum spanning tree. Then selects edges forming acyclic subgraph connecting all vertices. Grows tree by adding one vertex and one edge per iteration. And finally it continues until all vertices included, resulting in complete minimumm spanning tree.

#### Example with small graph in my project

Let's get small graph number 5. It's a great example of weighted, undirected graph. In graph each node is represented by an id. Each edge connects two nodes like source, target and includes a weight as well.

`{ "source": 1, "target": 5, "weight": 6 }`

### Implementing Prim's algorithm

I started by initializing data structures. And added to the priority queue after starting with an arbitrary vertex and setting its key to 0. If the priority queue is not empty the algorithm takes the minimum key vertex out of the priority queue, then indicates which vertex is part of the minimum spanning tree, add the updated vertices to the priority queue after updating the key values of the nearby non-tree vertices. Lastly, a minimum spanning tree was created using the parent array.

### Prim's algorithm complexity

Standard implementation with binary heap priority queue has O(VlogV + ElogV) time complexity. The V means vertices and E edges. Each vertex extracted from queue once O(VlogV). And if we add the Fibonacci head it improves time complexity to O(E + VlogV). 

#### Time complexity of my Prim's algorithm in this project

At first my prim's algorithm uses Min-priority queue and the Adjacency list representation for the graph. Typically the time complexity is `O(E + V (logV))`.

#### Detailed explanation

In detailed explanation why the complexity is `O(E + V (logV))`, because building the adjacency list and the node index map takes O(V + E) time. The loop runs at mist V time because each vertex is visited exactly once. `pq.poll()` this operation is exactly performed V times. With a standard binary head implementation of a Priority Queue, this takes O(logV) time, where the V is the of queue. Total time from polling is O(VlogV). `pq.add()` an insertion occurs for every edge connected to the currently processed node, provided by neighbor is not yet visited. Since every edge is checked twice, and the finally number of addition to the priority queue is bounded by 2E. Each insertion takes O(logV) times, which means O(logV) or O(logE) at one time. Total time from insertion is `O(ElogV)`.

#### Total time complexity

`O(initialization) + O(polling) + O(insertion) = O(V + O) + O(V logV) + O(E logV) = O(ElogV + VlogV)`

### Space complexity and comparisons

Space complexity typically O(V+E) for graph storage and additional data structures. Compare with Kruskal's algorithm there two algorithm takes the same space complexity. The adjacency list stores entry for every vertex and an entry for every large. This is the largest memory consumer, requiring O(V + E) space. Priority Queue stores of adjacent, unvisited nodes and their edge weights. In the worst case, it can hold up to E elements, but practically, the maximum size is bounding by E. And the boolean array tracks which vertices have been included in the minimum spanning tree. Finally list stores the result. Since an minimum spanning tree has exactly `V-1` edges. 

#### The total space complexity

`O(V+E) + O(V) + O(E) + O(V) + O(V) = O(V+E)`

### Where it is uses?

#### Transportation

Use for logistics network or transportation route optimization. Locations are represented by vertices, and routes with related expenses are represented by edges. For instance, design an effective road system that connects several cities while lowering the overall cost of construction.

#### Cluster Analysis

Use clustering algorithms to identify patterns and analyze data. To find clusters, create a minimum spanning tree of the data points. For targeted campaigns, group similar customers in the marketing database. 

#### Image Processing

Use in tasks involving object recognition and image segmentation. Use pixel similarities as edge weights and image pixels as vertices. Example: To recognize and delineate particular anatomical structures, segment medical images. 

### Time complexity of Kruskal's algorithm

The time complexity of Kruskal's algorithm is O(E logE) or O(E logV). Here, V stands for the number of vertices and E for the number of edges.

When applied to disconnected graphs, Kruskal's algorithm can generate a minimum spanning forest. It indicates that each connected component's minimum spanning trees make up the minimum spanning forest.

So the total time complexity is `O(E logV)`.

### Space complexity

The total space complexity is determined by the size of the primary data structures needed to store the graph and run the algorithm like storing edges, disjoint set union. The algorithm requires space to store all E edges of the input graph, typically in an array or list, so they can be sorted. This is the largest space requirement related to the input size. The DSU structure union-find is crucial for cycle detection. It usually involves two arrays or maps, each of size V like an array for the parent pointer of each vertex and an array for the rank or size of each set. This requires O(V) space. 

#### Total space complexity

The Minimum Spanning Tree itself will contain exactly V−1 edges. Storing the final list of minimum spanning tree edges requires O(V) space.

#### Performance in different graph types

Kruskal's algorithm demonstrates higher efficiency for sparse graph. The processes edges in sorted order without considering connectivity. It performs well when `|E| = |V|`. 

Kruskal's algorithm requires sorting all edges, potentially creating a bottleneck for very large graphs. It means that sorting can become time-consuming as the number of edges increses and it may impact performance for graphs with a high edge count. 

### Where it uses?

#### Telecommunication Networks

Used to lay fiber optic cables or telephone lines connecting several cities or stations while minimizing the total cable length or cost.

#### Computer Networks

Designing Local Area Networks where the goal is to connect all nodes with the fewest possible connections or the lowest latency.

#### Road and Rail Planning

Determining the minimum total distance of new roads or railway tracks required to link a set of towns or hubs.

