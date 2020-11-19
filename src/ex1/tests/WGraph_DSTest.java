package ex1.tests;


import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {




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

        graph.addNode(0);
        assertTrue(graph.nodeSize() == 4);
        graph.addNode(1);
        graph.addNode(-1);
        graph.addNode(-1);
        graph.addNode(-5);
        assertTrue(graph.nodeSize() == 4);
        graph.addNode(10);
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
        weighted_graph graph = new WGraph_DS();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(5);
        graph.addNode(10);
        int[] arr = new int[5];
        int[] ansArr = {0,1,2,5,10};
        int i = 0;
        for(node_info n : graph.getV())
        {
            arr[i] = n.getKey();
            ++i;
        }

        assertArrayEquals(ansArr,arr);


    }

    @Test
    void testGetV() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(10);
        graph.addNode(11);
        graph.addNode(12);
        graph.addNode(13);
        graph.addNode(14);
        graph.addNode(15);
        graph.connect(10,11,2);
        graph.connect(10,12,3);
        graph.connect(10,13,4);
        graph.connect(10,15,2);
        graph.connect(11,12,2);
        int[] ansArr = {11,12,13,15};
        int[] arr = new int[4];
        int i = 0;
        for(node_info n : graph.getV(10))
        {
            arr[i] = n.getKey();
            ++i;
        }
        assertArrayEquals(ansArr,arr);
    }

    @Test
    void removeNode() {
        weighted_graph graph = new WGraph_DS();
        assertTrue(graph.nodeSize() == 0);
        graph.addNode(0);
        assertTrue(graph.nodeSize() == 1);
        graph.removeNode(1);
        assertTrue(graph.nodeSize() == 1);
        graph.removeNode(0);
        assertTrue(graph.nodeSize() == 0);
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.connect(0,1,2);
        graph.connect(0,2,3);
        graph.connect(0,3,4);
        graph.connect(0,4,2);
        graph.connect(0,5,2);
        graph.connect(1,2,2);
        graph.removeNode(0);
        assertFalse(graph.hasEdge(0,1));
        assertFalse(graph.hasEdge(0,2));
        assertFalse(graph.hasEdge(0,3));
        assertFalse(graph.hasEdge(0,4));
        assertFalse(graph.hasEdge(0,5));
        assertTrue(graph.hasEdge(1,2));
        assertTrue(graph.getEdge(0,1) == -1);


    }

    @Test
    void removeEdge() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(0);
        graph.addNode(1);
        assertTrue(graph.edgeSize() == 0);
        assertFalse(graph.hasEdge(0,1));
        graph.connect(0,1,2);
        assertTrue(graph.hasEdge(0,1));
        assertTrue(graph.edgeSize() == 1);
        graph.removeEdge(1,0);
        assertTrue(graph.edgeSize() == 0);
        assertFalse(graph.hasEdge(0,1));



    }

    @Test
    void nodeSize() {
        weighted_graph graph = new WGraph_DS();
        assertTrue(graph.nodeSize() == 0);
        graph.addNode(0);
        assertTrue(graph.nodeSize() == 1);
        graph.addNode(1);
        assertTrue(graph.nodeSize() == 2);
        graph.removeNode(0);
        assertTrue(graph.nodeSize() == 1);


    }

    @Test
    void edgeSize() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        assertTrue(graph.edgeSize() == 0);
        graph.connect(0,1,2);
        graph.connect(0,2,3);
        graph.connect(0,3,4);
        graph.connect(0,4,2);
        graph.connect(0,5,2);
        graph.connect(1,2,2);
        graph.connect(1,2,2);
        graph.connect(0,3,4);

        assertTrue(graph.edgeSize() == 6);
        graph.removeNode(0);
        assertTrue(graph.edgeSize() == 1);
        graph.removeNode(1);
        assertTrue(graph.edgeSize() == 0);
    }

    @Test
    void getMC() {
        weighted_graph graph = new WGraph_DS();
        assertTrue(graph.getMC() == 0);
        graph.addNode(0);
        assertTrue(graph.getMC() == 1);
        graph.addNode(1);
        assertTrue(graph.getMC() == 2);
        graph.addNode(2);
        assertTrue(graph.getMC() == 3);
        graph.addNode(3);
        assertTrue(graph.getMC() == 4);
        graph.addNode(4);
        assertTrue(graph.getMC() == 5);
        graph.addNode(5);
        assertTrue(graph.getMC() == 6);
        graph.connect(0,1,2);
        assertTrue(graph.getMC() == 7);
        graph.connect(0,2,3);
        assertTrue(graph.getMC() == 8);
        graph.connect(0,3,4);
        assertTrue(graph.getMC() == 9);
        graph.connect(0,4,2);
        assertTrue(graph.getMC() == 10);
        graph.connect(0,5,2);
        assertTrue(graph.getMC() == 11);
        graph.connect(1,2,2);
        assertTrue(graph.getMC() == 12);
        graph.connect(1,2,2);
        assertTrue(graph.getMC() == 12);
        graph.connect(1,2,4);
        assertTrue(graph.getMC() == 13);
        graph.connect(0,3,4);
        assertTrue(graph.getMC() == 13);

        assertTrue(graph.edgeSize() == 6);
        graph.removeNode(0);
        assertTrue(graph.edgeSize() == 1);
        graph.removeNode(1);
        assertTrue(graph.edgeSize() == 0);
    }

    @Test
    void TestRunTime()
    {
        long start = new Date().getTime();
        int numNode = 1000000;
        int numEdge = numNode*10;
        weighted_graph graph = new WGraph_DS();
        for(int i = 0; i<numNode ; i++)
        {
            graph.addNode(i);
        }
        assertTrue(graph.nodeSize() == 1000000);
        int i = 0;
        while(numEdge > graph.edgeSize())
        {
            for(node_info n : graph.getV())
            {
                if(i > numNode)
                    i=0;
                if(numEdge <= graph.edgeSize())
                    break;
                graph.connect(n.getKey(),i,10);
            }
            i++;
        }
        long end = new Date().getTime();
        double dt = (end-start)/1000.0;
        assertTrue(dt < 10.0);//4.3 sec
        //System.out.println(dt);

    }


}