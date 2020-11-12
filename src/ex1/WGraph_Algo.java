package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {

    private weighted_graph graph;

    public WGraph_Algo()
    {
        graph = null;
    }

    public WGraph_Algo(weighted_graph g)
    {
        init(g);
    }


    @Override
    public void init(weighted_graph g) {
        graph = g;
    }

    @Override
    public weighted_graph getGraph() {
        return graph;
    }

    @Override
    public weighted_graph copy() {
        WGraph_DS copyGraph = new WGraph_DS(graph);
        return copyGraph;
    }

    @Override
    public boolean isConnected() {
        if(graph.nodeSize() == 0)
            return true;
        dijkstra(graph.getV().iterator().next());
        for(node_info ni : graph.getV())
        {
            if(ni.getInfo().equals("WHITE"))
                return false;
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if(graph.getNode(src) == null || graph.getNode(dest) == null)
            return -1;
        dijkstra(graph.getNode(src));
        return graph.getNode(dest).getTag();
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        LinkedList<node_info> list = new LinkedList<>();
        if(graph.getNode(src) == null || graph.getNode(dest) == null)
        {
            return null;
        }
        if(src == dest)
        {
            list.add(graph.getNode(src));
            return list;
        }
        HashMap<Integer,node_info> pv = dijkstra(this.graph.getNode(src));
        if(graph.getNode(dest).getInfo().equals("WHITE"))
        {
            return null;
        }

        list.addFirst(graph.getNode(dest));
        node_info t = pv.get(dest);

        while(t != null)
        {
            list.addFirst(graph.getNode(t.getKey()));
            t = pv.get(t.getKey());
        }

        return list;
    }

    @Override
    public boolean save(String file) {
        FileOutputStream fis = null;
        try {
            fis = new FileOutputStream(file);
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

    private HashMap<Integer, node_info> dijkstra(node_info node)
    {
        PriorityQueue<node_info> queue = new PriorityQueue<>();
        HashMap<Integer,node_info> mapPath = new HashMap<>();
        for(node_info ni : graph.getV())
        {
            ni.setTag(Double.MAX_VALUE);
            ni.setInfo("WHITE");
            mapPath.put(ni.getKey(),null);
            queue.add(ni);
        }
        node.setTag(0);

        while(!queue.isEmpty())
        {
            node_info n = queue.remove();
            for(node_info ni : graph.getV(n.getKey()))
            {
                if(ni.getInfo().equals("WHITE"))
                {
                    if(n.getTag() < Double.MAX_VALUE) {
                        double t = n.getTag() + graph.getEdge(n.getKey(), ni.getKey());
                        if (ni.getTag() > t) {
                            ni.setTag(t);
                            mapPath.put(ni.getKey(), n);
                            queue.remove(ni);
                            queue.add(ni);
                        }
                    }
                }
            }
            n.setInfo("BLACK");
        }
        return mapPath;

    }
}
