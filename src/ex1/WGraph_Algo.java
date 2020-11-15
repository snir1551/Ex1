package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    //
    private weighted_graph graph;

    /**
     * default constructor
     */
    public WGraph_Algo()
    {
        graph = null;
    }

    /**
     * Contstructor that get weighted_graph
     * init the graph
     * @param g - weighted_graph
     */
    public WGraph_Algo(weighted_graph g)
    {
        init(g); //init the graph
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        graph = g;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return graph;
    } //getter of graph

    /**
     * Compute a deep copy of this weighted graph.
     * @return weighted_graph - deep copy of the graph
     */
    @Override
    public weighted_graph copy() {
        WGraph_DS copyGraph = new WGraph_DS(graph); //use the copy constructor of WGraph_DS
        return copyGraph; // return the copy(deep copy) of the graph
    }
    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     * @return
     */
    @Override
    public boolean isConnected() {
        if(graph.nodeSize() == 0) //if the size nodes in the graph equal to 0 so we need to return true
            return true;
        dijkstra(graph.getV().iterator().next());//start dijkstra algorithem on the first node
        for(node_info ni : graph.getV()) // go through all of the vertexes
        {
            if(ni.getTag() >= Double.MAX_VALUE) // if one of the node is MAX_VALUE so we need to return false
                return false;
        }
        return true; //if all of the nodes are BLACK COLOR return true
    }
    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(graph.getNode(src) == null || graph.getNode(dest) == null) //if one of the nodes not exist return -1
            return -1;
        dijkstra(graph.getNode(src)); //start dijkstra algorithm on the (src node)
        if(graph.getNode(dest).getTag() >= Double.MAX_VALUE)
            return -1;
        return graph.getNode(dest).getTag(); // return the shortest path between them by the tag that contain the distance
    }
    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        LinkedList<node_info> list = new LinkedList<>(); //list of the path from src to dest
        if(graph.getNode(src) == null || graph.getNode(dest) == null) // if src or dest not exist return null
        {
            return null;
        }
        if(src == dest) //if src equal to dest
        {
            list.add(graph.getNode(src)); //add src to list
            return list; //return list
        }
        HashMap<Integer,node_info> pv = dijkstra(this.graph.getNode(src));//start dijkstra on src and return hashmap that contain the path fathers
        if(graph.getNode(dest).getInfo().equals("WHITE")) //if the dest is WHITE so we didnt move on him so return null
        {
            return null;
        }

        list.addFirst(graph.getNode(dest)); //add to list dest
        node_info t = pv.get(dest); // t = next node

        while(t != null)
        {
            list.addFirst(graph.getNode(t.getKey())); // add the t to list
            t = pv.get(t.getKey()); // t = next node
        }

        return list;
    }
    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        FileOutputStream fis = null; //open stream
        try {
            fis = new FileOutputStream(file); //let him the file name
            ObjectOutputStream ois = new ObjectOutputStream(fis);
            ois.writeObject(graph);
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            graph = (weighted_graph)ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * Algorithm dijksta:
     * It goes over each vertex in the graph start with start node
     * and changes the tag's neighbors to the tag to be the shortest distance
     * and after finishing working on the vertex it paints it black and goes over all the vertices like this
     *
     * White color - not visited nodes
     * Black color - visited nodes
     * @param node start node in the graph that we operate on him dijkstra
     * @return HashMap<Integer, node_info> that contains the father path
     */
    private HashMap<Integer, node_info> dijkstra(node_info node)
    {
        PriorityQueue<node_info> queue = new PriorityQueue<>(); // init PriorityQueue of node_info
        HashMap<Integer,node_info> mapPath = new HashMap<>(); //init HashMap of key: Integer , value: node_info
        for(node_info ni : graph.getV()) //We go through all the vertices
        {
            ni.setTag(Double.MAX_VALUE); //set their tag to Max_Value
            ni.setInfo("WHITE"); //  set their info to WHITE
            mapPath.put(ni.getKey(),null); //put in our HashMap (father path)  - key: key of the node , value: null
            queue.add(ni); //add to our PriorityQueue the node
        }
        node.setTag(0); //set tag of our start node to be 0
        queue.remove(node);//decreaseKey - we're removing the node and add him back
        queue.add(node);
        while(!queue.isEmpty()) // while our PriorityQueue is not empty
        {
            node_info n = queue.remove(); //remove our node that we're working on him
            for(node_info ni : graph.getV(n.getKey())) //We go through all his neighbors
            {
                if(ni.getInfo().equals("WHITE")) //if he is WHITE We never went through it
                {
                    if(n.getTag() < Double.MAX_VALUE) { //if tag smallest than MAX_VALUE
                        double t = n.getTag() + graph.getEdge(n.getKey(), ni.getKey());
                        if (ni.getTag() > t) { //if the tag of the neighbor bigger than new path tag so update the neighbor tag
                            ni.setTag(t); //neighbor tag to be t
                            mapPath.put(ni.getKey(), n); //update the father path
                            queue.remove(ni);//decreaseKey - we're removing the node and add him back
                            queue.add(ni);
                        }
                    }
                }
            }
            n.setInfo("BLACK"); //we finish with the node set info to BLACK
        }
        return mapPath; //return the father path

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_Algo that = (WGraph_Algo) o;
        return Objects.equals(graph, that.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph);
    }
}
