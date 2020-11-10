package ex1;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {

    private HashMap<Integer,HashMap<Integer,edge_info>> mapNeighbors;
    private HashMap<Integer,node_info> mapNodeInfo;
    private int edgeCounter; // count number of edges in the graph
    private int MC; // count number of the changes in the graph
    public WGraph_DS()
    {
        edgeCounter = 0;
        MC = 0;
        mapNeighbors = new HashMap<>();
        mapNodeInfo = new HashMap<>();
    }
    //O(N+M)
    public WGraph_DS(weighted_graph wgraph)
    {
        this();
        for(node_info ni : wgraph.getV())
        {
            addNode(ni.getKey());
        }
        for(node_info ni : wgraph.getV())
        {
            for(node_info n : wgraph.getV(ni.getKey()))
            {
                this.connect(n.getKey(),ni.getKey(),getEdge(n.getKey(),ni.getKey()));
            }
        }
        this.MC = wgraph.getMC();
    }

    @Override
    public node_info getNode(int key) {
        return mapNodeInfo.get(key); //
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if(mapNodeInfo.containsKey(node1))
            return mapNeighbors.get(node1).containsKey(node2);
        else
            return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2))
        {
            return mapNeighbors.get(node1).get(node2).getWeight();
        }
        return -1;
    }

    @Override
    public void addNode(int key) {
        if(mapNodeInfo.containsKey(key))
            throw new RuntimeException();
        mapNodeInfo.put(key,new NodeInfo(key));
        mapNeighbors.put(key,new HashMap<>());
        ++MC;
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if(w < 0)
            throw new RuntimeException();
        if(hasEdge(node1,node2))
            throw new RuntimeException();
        mapNeighbors.get(node1).put(node2,new EdgeInfo(w));
        mapNeighbors.get(node2).put(node1,new EdgeInfo(w));
        ++edgeCounter;
        ++MC;
    }

    @Override
    public Collection<node_info> getV() {
        return mapNodeInfo.values();
    }

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

    @Override
    public int nodeSize() {
        return mapNodeInfo.size();
    }

    @Override
    public int edgeSize() {
        return edgeCounter;
    }

    @Override
    public int getMC() {
        return MC;
    }

    private static class NodeInfo implements node_info,Comparable<node_info>
    {

        private int _key; //key for this node
        private String _info; //data of this node
        private double _tag; //
        private static int _countNodes = 0; //this countNodes let new key for new node
        public NodeInfo()
        {
            this._key = _countNodes;
            ++_countNodes;
            this._info = null;
            this._tag = 0;
        }
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
