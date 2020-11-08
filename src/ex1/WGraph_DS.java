package ex1;

import java.util.Collection;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph {
    @Override
    public node_info getNode(int key) {
        return null;
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        return 0;
    }

    @Override
    public void addNode(int key) {

    }

    @Override
    public void connect(int node1, int node2, double w) {

    }

    @Override
    public Collection<node_info> getV() {
        return null;
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        return null;
    }

    @Override
    public node_info removeNode(int key) {
        return null;
    }

    @Override
    public void removeEdge(int node1, int node2) {

    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }

    private class NodeInfo implements  node_info
    {

        private int _key;
        private String _info;
        private double _tag;
        private int _countNodes;
        private HashMap<Integer,node_info> mapNode;
        public NodeInfo()
        {
            mapNode = new HashMap<>();
            _key = _countNodes++;
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
    }
}
