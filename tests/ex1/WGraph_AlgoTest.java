package ex1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    //pictures/graph1
    /*
        our graph:
                [1]->[7]->[5]
                [2]
                [5]->[7]->[1]
     */
    private weighted_graph buildFirstGraph()
    {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(5);
        graph.connect(1,5,7);
        return graph;
    }

    //pictures/graph2
    /*
        our graph:
                    [1]->[5,20]->[10,11]
                    [2]->[]
                    [5]->[1]->[10]
                    [7]->[9]->[10]
                    [10]->[5,1,9]->[1,5,7]
                    [11]->[20]->[1]
     */
    private weighted_graph buildSecondGraph()
    {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(5);
        graph.addNode(7);
        graph.addNode(10);
        graph.addNode(11);
        graph.connect(1,10,5);
        graph.connect(5,10,1);
        graph.connect(1,11,20);
        graph.connect(7,10,9);
        return graph;
    }

    //pictures/graph3
    /*
        [1]->[6,3,2]
        [2]->[1,3,4]
        [3]->[1,2,4,6]
        [4]->[2,3,5]
        [5]->[4,6]
        [6]->[1,3,5]
     */
    private weighted_graph buildThirdGraph()
    {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.connect(1,2,7);
        graph.connect(1,6,14);
        graph.connect(1,3,9);
        graph.connect(2,3,10);
        graph.connect(2,4,15);
        graph.connect(3,4,11);
        graph.connect(3,6,2);
        graph.connect(6,5,9);
        graph.connect(4,5,6);
        return graph;
    }

    @Test
    void init() {
        weighted_graph graph = buildFirstGraph();
        weighted_graph_algorithms graph_algo = new WGraph_Algo();
        graph_algo.init(graph);
        assertTrue(graph_algo.getGraph() == graph);
        graph = buildSecondGraph();
        assertTrue(graph_algo.getGraph() != graph);
        graph_algo.init(graph);
        assertTrue(graph_algo.getGraph() == graph);
        graph = buildThirdGraph();
        assertTrue(graph_algo.getGraph() != graph);
        graph_algo.init(graph);
        assertTrue(graph_algo.getGraph() == graph);



    }

    @Test
    void getGraph() {
        weighted_graph_algorithms graph_algo = new WGraph_Algo();
        assertNull(graph_algo.getGraph());
        weighted_graph graph = buildFirstGraph();
        weighted_graph_algorithms graph_algo1 = new WGraph_Algo(graph);
        weighted_graph graph1 = graph_algo1.getGraph();
        assertTrue(graph1.nodeSize() == graph.nodeSize());
        assertTrue(graph1.edgeSize() == graph.edgeSize());
        assertTrue(graph1.hasEdge(1,5));
        assertFalse(graph1.hasEdge(1,2));
        graph.addNode(6);
        assertTrue(graph1.nodeSize() == graph.nodeSize());
        graph.connect(1,2,1);
        assertTrue(graph1.hasEdge(1,2));
        graph1.removeEdge(1,5);
        assertFalse(graph.hasEdge(1,5));
        assertFalse(graph1.hasEdge(1,5));


    }

    @Test
    void copy() {
        weighted_graph graph1 = buildFirstGraph();
        weighted_graph_algorithms graph_algo1 = new WGraph_Algo(graph1);
        weighted_graph copyGraph1 = graph_algo1.copy();
        assertEquals(graph1,copyGraph1);
        graph1.addNode(1000);
        assertNotEquals(graph1,copyGraph1);
        copyGraph1.addNode(1000);
        assertEquals(graph1,copyGraph1);
        copyGraph1.addNode(1001);
        assertNotEquals(graph1,copyGraph1);
        graph1.addNode(1001);
        graph1.connect(1000,1001,10);
        assertNotEquals(graph1,copyGraph1);


    }

    @Test
    void isConnected() {
        weighted_graph graph = buildSecondGraph();
        weighted_graph_algorithms graph_algo = new WGraph_Algo(graph);
        assertFalse(graph_algo.isConnected());
        graph_algo.init(buildFirstGraph());
        assertFalse(graph_algo.isConnected());
        graph_algo.init(buildThirdGraph());
        assertTrue(graph_algo.isConnected());

    }

    @Test
    void shortestPathDist() {
        weighted_graph graph = buildThirdGraph();
        weighted_graph_algorithms graph_algo = new WGraph_Algo(graph);
        assertTrue(graph_algo.shortestPathDist(1,5) == 20);
        assertTrue(graph_algo.shortestPathDist(1,1) == 0);
        assertTrue(graph_algo.shortestPathDist(2000,5) == -1);
        assertTrue(graph_algo.shortestPathDist(5,2000) == -1);

    }

    @Test
    void shortestPath() {
        weighted_graph graph = buildThirdGraph();
        weighted_graph_algorithms graph_algo = new WGraph_Algo(graph);
        List<node_info> path = graph_algo.shortestPath(1,5);
        int[] arr = new int[4];
        int[] ansArr = {1,3,6,5};
        int i = 0;
        for(node_info n : path)
        {
            arr[i] = n.getKey();
            ++i;
        }
        assertArrayEquals(ansArr,arr);
    }


    @Test
    void saveANDload()
    {
        weighted_graph graph = buildThirdGraph();
        weighted_graph_algorithms graph_algo = new WGraph_Algo(graph);
        graph_algo.save("test1");
        weighted_graph graph1 = new WGraph_DS();
        weighted_graph_algorithms graph_algo1 = new WGraph_Algo(graph);
        graph_algo1.load("test1");

        assertEquals(graph_algo,graph_algo1);
    }
}