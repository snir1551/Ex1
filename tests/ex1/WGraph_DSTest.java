package ex1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {





    @BeforeAll
    public static void graphInit()
    {

    }
    @Test
    void getNode() {
        weighted_graph graph = new WGraph_DS();
        assertNull(graph.getNode(0),"not exist node"); //not exist this node in the graph
        graph.addNode(0); // add node 0 to the graph
        assertNotNull(graph.getNode(0)); //we check that we didnt return null because we added the node
        assertNull(graph.getNode(-1)); //not exist this node in the graph
        assertNull(graph.getNode(-2)); //not exist this node in the graph
        graph.addNode(-1);
        assertNotNull(graph.getNode(-1)); //we check that we didnt return null because we added the node
        assertNull(graph.getNode(-2)); //not exist this node in the graph
        graph.addNode(2);
        assertNull(graph.getNode(-2));
        assertNotNull(graph.getNode(2));
    }

    @Test
    void hasEdge() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        assertFalse(graph.hasEdge(0,1));
        assertFalse(graph.hasEdge(0,2));
        assertFalse(graph.hasEdge(0,3));
        assertFalse(graph.hasEdge(1,2));
        assertFalse(graph.hasEdge(2,3));
        assertFalse(graph.hasEdge(0,0));
        assertFalse(graph.hasEdge(1,1));
        graph.connect(0,1,1);
        assertTrue(graph.hasEdge(0,1));
        graph.connect(0,3,1);
        assertTrue(graph.hasEdge(3,0));
        graph.connect(0,0,1);
        assertFalse(graph.hasEdge(0,0));
        graph.connect(3,3,0);
        assertFalse(graph.hasEdge(3,3));

    }

    @Test
    void getEdge() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.connect(0,1,1);
        assertTrue(graph.getEdge(1,0) == 1);
        graph.connect(0,1,2);
        assertTrue(graph.getEdge(1,0) == 2); //update weight of edge between node0 and node1
        graph.connect(0,1,Integer.MAX_VALUE);
        assertTrue(graph.getEdge(0,1) == Integer.MAX_VALUE); //update weight of edge between node0 and node1
        assertTrue(graph.getEdge(-1,0) == -1); // not exist edge between them so we dont have weight return -1
        assertTrue(graph.getEdge(2,0) == -1); // not exist edge between them so we dont have weight return -1
        assertTrue(graph.getEdge(0,3) == -1); // not exist edge between them so we dont have weight return -1
    }

    @Test
    void addNode() {
        weighted_graph graph = new WGraph_DS();
        assertTrue(graph.nodeSize() == 0);
        graph.addNode(0);
        assertTrue(graph.nodeSize() == 1);
        graph.addNode(1);
        graph.addNode(-1);
        graph.addNode(-5);
        assertTrue(graph.nodeSize() == 4);
        try {
            graph.addNode(0);
            fail("your node already exist in the graph");
        }
        catch (Exception e) {
            assertTrue(true);
        }
        try {
            graph.addNode(1);
            fail("your node already exist in the graph");
        }
        catch (Exception e) {
            assertTrue(true);
        }
        try {
            graph.addNode(-1);
            fail("your node already exist in the graph");
        }
        catch (Exception e) {
            assertTrue(true);
        }
        try {
            graph.addNode(-1);
            fail("your node already exist in the graph");
        }
        catch (Exception e) {
            assertTrue(true);
        }
        try {
            graph.addNode(-5);
            fail("your node already exist in the graph");
        }
        catch (Exception e) {
            assertTrue(true);
        }
        assertTrue(graph.nodeSize() == 4);
        try {
            graph.addNode(10);
            assertTrue(true);
        }
        catch (Exception e) {
            assertTrue(false);
        }
        assertTrue(graph.nodeSize() == 5);
    }

    @Test
    void connect() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(5);
        graph.addNode(7);
        graph.addNode(22);
        graph.addNode(66);
        assertTrue(graph.edgeSize() == 0);
        graph.connect(5, 7,0);
        assertTrue(graph.edgeSize() == 1);
        assertTrue(graph.hasEdge(5,7));
        assertTrue(graph.edgeSize() == 1);
        graph.connect(5,22,Integer.MAX_VALUE);
        assertTrue(graph.edgeSize() == 2);
        try {
            graph.connect(66,5,-0.01);
            fail("your weight cant be negative");
        }
        catch (Exception e) {
            assertTrue(true);
        }
        assertTrue(graph.edgeSize() == 2);



    }

    @Test
    void getV() {
    }

    @Test
    void testGetV() {
    }

    @Test
    void removeNode() {

    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
        weighted_graph graph = new WGraph_DS();
        assertTrue(graph.nodeSize() == 0);
        graph.addNode(Integer.MAX_VALUE);
        assertTrue(graph.nodeSize() == 1);
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }
}