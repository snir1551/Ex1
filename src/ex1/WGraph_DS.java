package ex1;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {

    private HashMap<Integer,HashMap<Integer,edge_info>> mapNeighbors;//map of map that contain neighbors of node and his edge
    private HashMap<Integer,node_info> mapNodeInfo; // map that let us the node by the key
    private int edgeCounter; // count number of edges in the graph
    private int MC; // count number of the changes in the graph

    /**
     * Contrucor
     */
    public WGraph_DS()
    {
        edgeCounter = 0;
        MC = 0;
        mapNeighbors = new HashMap<>();
        mapNodeInfo = new HashMap<>();
    }

    /**
     * Copy Constructor O(N+M)
     */
    public WGraph_DS(weighted_graph wgraph)
    {
        this();
        for(node_info ni : wgraph.getV()) //go through all the vertices of the wgraph
        {
            addNode(ni.getKey()); //add them to this graph
            mapNodeInfo.get(ni.getKey()).setTag(ni.getTag());
            mapNodeInfo.get(ni.getKey()).setInfo(ni.getInfo());
        }
        for(node_info ni : wgraph.getV()) // go through all the vertices of the wgraph
        {
            for(node_info n : wgraph.getV(ni.getKey())) // Go through all the vertices of the neighbors of the wgraph
            {
                this.connect(n.getKey(),ni.getKey(),wgraph.getEdge(n.getKey(),ni.getKey())); //add for this graph edge like in wgraph
            }
        }
        this.MC = wgraph.getMC(); //count of changes in the graph need to be like wgraph
    }

    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return mapNodeInfo.get(key); //get the node_info by his key
    }

    /**
     *
     * @param node1 key of the node1
     * @param node2 key of the node2
     * NOTE: O(1)
     * @return iff exist edge between node1 to node2
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(mapNodeInfo.containsKey(node1)) //check if exist node1 in the mapNodeInfo
            return mapNeighbors.get(node1).containsKey(node2); //return true if exist edge between them else false
        else
            return false; // not exist node1 in the graph return false
    }

    /**
     * Note: this method should run in O(1) time.
     * @param node1 key of node1
     * @param node2 key of node2
     * Note: O(1)
     * @return if has edge between node1,node2 return the weight else return -1
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2)) // check if exist edge between node1 to node2
        {
            return mapNeighbors.get(node1).get(node2).getWeight(); // return the weight of the edge between node to node2
        }
        return -1; // if not exist node1 or if not exist edge between node1 to node2 return -1
    }
    /**
     * add a new node to the graph with the given key.
     * Note: O(1)
     * Note2: if there is already a node with such a key -> no action should be performed.
     * @param key
     */
    @Override
    public void addNode(int key) {
        if(mapNodeInfo.containsKey(key)) //check if node exist in the graph
            return; // do nothing if the node exist in the graph
        mapNodeInfo.put(key,new NodeInfo(key)); //add the node to the graph
        mapNeighbors.put(key,new HashMap<>()); //init his neighbors
        ++MC; //we added one node to the graph so we need to count++ the changes in the graph
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: O(1)
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(node1 != node2) { //if its not same node
            if (w < 0) //if the weight is negative throw exception
                throw new RuntimeException("your weight can't be negative");
            if(hasEdge(node1,node2) && getEdge(node1,node2) == w) //if exist edge between node1 to node2 and the edge between them equal to w
            {
                return;
            }
            else if(getNode(node2) != null && getNode(node1) != null)// else if node1 and node2 exist in the graph and they dont have edge
            {
                if(!hasEdge(node1,node2))
                    ++edgeCounter; //count of edge +1

                mapNeighbors.get(node1).put(node2, new EdgeInfo(w)); //add edge between node1 to node2 with weight (w)
                mapNeighbors.get(node2).put(node1, new EdgeInfo(w)); //add edge between node2 to node1 with weight (w)
                ++MC;

           }


        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: O(1)
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return mapNodeInfo.values(); // return pointer  Collection<node_info>
    }

    /**
     *
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: O(k) , k - being the degree of node_id.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        LinkedList<node_info> listNeighbors = new LinkedList<node_info>();
        if(mapNodeInfo.containsKey(node_id))
        {
            for(Integer n : mapNeighbors.get(node_id).keySet())
            {
                listNeighbors.addLast(mapNodeInfo.get(n));
            }
        }
        return listNeighbors;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_info removeNode(int key) {
        if(mapNodeInfo.containsKey(key))
        {
            for(Integer n : mapNeighbors.get(key).keySet())
            {
                mapNeighbors.get(n).remove(key);
                --edgeCounter;
                ++MC;
            }
            mapNeighbors.get(key).clear();
            ++MC;
        }

        return mapNodeInfo.remove(key);
    }

    /**
     * Delete the edge from the graph,
     * Note: O(1)
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1,node2))
        {
            mapNeighbors.get(node1).remove(node2);
            mapNeighbors.get(node2).remove(node1);
            --edgeCounter;
            ++MC;
        }
    }

    /**
     * Note: O(1)
     * @return the number of vertices (nodes) in the graph.
     */
    @Override
    public int nodeSize() {
        return mapNodeInfo.size();
    }

    /**
     * Note: O(1)
     * @return the number of edges (undirectional graph).
     */
    @Override
    public int edgeSize() {
        return edgeCounter;
    }

    /**
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * Note: O(1)
     * @return the Mode Count - for testing changes in the graph.
     */
    @Override
    public int getMC() {
        return MC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return edgeCounter == wGraph_ds.edgeCounter &&
                MC == wGraph_ds.MC &&
                Objects.equals(mapNeighbors, wGraph_ds.mapNeighbors) &&
                Objects.equals(mapNodeInfo, wGraph_ds.mapNodeInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mapNeighbors, mapNodeInfo, edgeCounter, MC);
    }

    public String toString()
    {
        String ans = "";
        for(node_info node : getV())
        {
            ans += "[" + node.getKey() + "] -> [";
            int num = nodeSize() - 1;
            for(node_info n : getV(node.getKey())) {
                ans += n.getKey() + " (" + getEdge(n.getKey(),node.getKey()) + ") ";
                if(num > 0) {
                    ans += ", ";
                    --num;
                }
            }
            ans += "] [tag: " + node.getTag() + "]\n";

        }
        return ans;

    }

    private static class NodeInfo implements node_info,Comparable<node_info>,Serializable
    {

        private int _key; //key for this node
        private String _info; //data of this node
        private double _tag; //
        //private static int _countNodes = 0; //this countNodes let new key for new node
//        public NodeInfo()
//        {
//            this._key = _countNodes;
//            //++_countNodes;
//            this._info = null;
//            this._tag = 0;
//        }
        public NodeInfo(int key)
        {
            this._key = key;
            this._info = null;
            this._tag = 0;
        }

        @Override
        public int getKey() {
            return this._key;
        }


        @Override
        public String getInfo() {
            return this._info;
        }

        @Override
        public void setInfo(String s) {
            this._info = s;
        }

        @Override
        public double getTag() {
            return this._tag;
        }

        @Override
        public void setTag(double t) {
            this._tag = t;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return _key == nodeInfo._key &&
                    Double.compare(nodeInfo._tag, _tag) == 0 &&
                    Objects.equals(_info, nodeInfo._info);
        }

        @Override
        public int hashCode() {
            return Objects.hash(_key, _info, _tag);
        }

        @Override
        public int compareTo(node_info o) {
            if(this.getTag() > o.getTag())
                return 1;
            else if(this.getTag() == o.getTag())
                return 0;
            else
                return -1;
        }
    }
}
