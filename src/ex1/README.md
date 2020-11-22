# Ex1_OOP

This project is about a weighted and undirected graph that is a set of vertices and edges which connect the vertices, 
<br>
edge is actually neighbor vertex of this and this is actually neighbor of the vertex.

in this project we have four classes: EdgeInfo,NodeInfo, WGraphDS, WGraphAlgo.
<br>
for each of the classes we have interface:
- for EdgeInfo class we have edge_info interface
- for NodeInfo class we have node_info interface
- for WGraphDS class we have weighted_graph interface
- for WGraph_Algo class we have weighted_graph_algorithms interface
<br>
class EdgeData: he is edge class that contain the weight of the edge and info for the edge
<br>
class NodeInfo: he is private inner class in WGraphDS class, vertex of the graph
<br>
class WGraphDS: builds the graph by EdgeInfo,NodeInfo
<br>
class WGraph_Algo: this class does all the complex calculations in the graph
<br>
Examples of a weighted and undirected graph:

<img src="https://github.com/snir1551/Ex1_OOP/blob/master/src/ex1/pictures/graph1.png"  width="250"> 

<img src="https://github.com/snir1551/Ex1_OOP/blob/master/src/ex1/pictures/graph2.png"  width="250">

<img src="https://github.com/snir1551/Ex1_OOP/blob/master/src/ex1/pictures/graph3.png"  width="250">



Algorithm dijkstra explanation:

<img src="https://github.com/snir1551/Ex1_OOP/blob/master/src/ex1/pictures/dijkstra1.png"  width="250">  <img        src="https://github.com/snir1551/Ex1_OOP/blob/master/src/ex1/pictures/dijkstra2.png"  width="250"> <img src="https://github.com/snir1551/Ex1_OOP/blob/master/src/ex1/pictures/dijkstra3.png"  width="250">  <img src="https://github.com/snir1551/Ex1_OOP/blob/master/src/ex1/pictures/dijkstra4.png"  width="250">  <img src="https://github.com/snir1551/Ex1_OOP/blob/master/src/ex1/pictures/dijkstra5.png"  width="250">  <img src="https://github.com/snir1551/Ex1_OOP/blob/master/src/ex1/pictures/dijkstra6.png"  width="250"> 

White color - not visited nodes<br>
Black color - visited nodes<br>

for more information: https://github.com/snir1551/Ex1_OOP/wiki