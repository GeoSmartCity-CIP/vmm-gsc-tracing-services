package be.vmm.gsc.tracing.test;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * Created by s.vanmieghem on 15/12/2015.
 */
public class SampleNetwork {

    /**
     * 1 node, no edge
     */
    public static DirectedGraph<Integer, String> eennode() {
        DirectedGraph<Integer, String> graph = new DefaultDirectedGraph<Integer, String>(String.class);
        graph.addVertex(1);
        return graph;
    }


    /**
     * 1 edge
     * 1--A--2
     */
    public static DirectedGraph<Integer, String> eenlink() {
        DirectedGraph<Integer, String> graph = new DefaultDirectedGraph<Integer, String>(String.class);
        for (int i = 1; i <= 2; i++) graph.addVertex(i);

        graph.addEdge(1, 2, "A");
        return graph;
    }

    /**
     * Graph with two edges between two nodes
     * 1--A--2
     *  \-B-/
     */
    public static DirectedGraph<Integer, String> dubbel() {
        DirectedGraph<Integer, String> graph = new DefaultDirectedGraph<Integer, String>(String.class);
        for (int i = 1; i <= 2; i++) graph.addVertex(i);

        graph.addEdge(1, 2, "A");
        graph.addEdge(1, 2, "B");
        return graph;
    }


    /**
     * Multiple disconnected graphs
     * 1--A--2
     * 3--B--4
     */
    public static DirectedGraph<Integer, String> multi() {
        DirectedGraph<Integer, String> graph = new DefaultDirectedGraph<Integer, String>(String.class);
        for (int i = 1; i <= 4; i++) graph.addVertex(i);

        graph.addEdge(1, 2, "A");
        graph.addEdge(3, 4, "B");
        return graph;
    }

    /*
     * Tree, downstream (delta)
     *
     * 1--A--2--B--3--D--5
     *        \-C--4--E--6--F--7
     *                    \-G--8
     */
    public static DirectedGraph<Integer, String> boomStroomafwaarts() {
        DirectedGraph<Integer, String> graph = new DefaultDirectedGraph<Integer, String>(String.class);
        for (int i=1;i<=8;i++) graph.addVertex(i);

        graph.addEdge(1,2,"A");
        graph.addEdge(2,3,"B");
        graph.addEdge(2,4,"C");
        graph.addEdge(3,5,"D");
        graph.addEdge(4,6,"E");
        graph.addEdge(6,7,"F");
        graph.addEdge(6,8,"G");

        return graph;
    }

    /*
     * Tree, upstream
     * Mirror of previous sample
     */
    public static DirectedGraph<Integer, String> boomStroomopwaarts() {
        DirectedGraph<Integer, String> graph = new DefaultDirectedGraph<Integer, String>(String.class);
        for (int i=1;i<=8;i++) graph.addVertex(i);

        graph.addEdge(2,1,"A");
        graph.addEdge(3,2,"B");
        graph.addEdge(4,2,"C");
        graph.addEdge(5,3,"D");
        graph.addEdge(6,4,"E");
        graph.addEdge(7,6,"F");
        graph.addEdge(8,6,"G");

        return graph;
    }


    /*
     * Hourglass, multiple incomming, multiple outgoing edges
     *
     * 1--A-\ /-D--5
     * 2--B--4--E--6
     * 3--C-/ \-F--7
     */
    public static DirectedGraph<Integer, String> zandloper() {
        DirectedGraph<Integer, String> graph = new DefaultDirectedGraph<Integer, String>(String.class);
        for (int i=1;i<=7;i++) graph.addVertex(i);

        graph.addEdge(1,4,"A");
        graph.addEdge(2,4,"B");
        graph.addEdge(2,5,"C");
        graph.addEdge(4,5,"D");
        graph.addEdge(4,6,"E");
        graph.addEdge(4,7,"F");

        return graph;
    }

    /**
     * Parallel routes
     *
     * 1--A--2--B--3--C--4
     *  \--D----5----E--/
     *
     * @return
     */
    public static DirectedGraph<Integer, String> parallelPad() {
        DirectedGraph<Integer, String> graph = new DefaultDirectedGraph<Integer, String>(String.class);
        for (int i=1;i<=5;i++) graph.addVertex(i);

        graph.addEdge(1,2,"A");
        graph.addEdge(2,3,"B");
        graph.addEdge(3,4,"C");
        graph.addEdge(1,5,"D");
        graph.addEdge(5,4,"E");

        return graph;
    }

    /**
     * Loop
     *
     * 1--A--2--B--3--C->4--F--6
     *  \<-E----5----D--/
     *
     * @return
     */
    public static DirectedGraph<Integer, String> lus() {
        DirectedGraph<Integer, String> graph = new DefaultDirectedGraph<Integer, String>(String.class);
        for (int i=1;i<=6;i++) graph.addVertex(i);

        graph.addEdge(1,2,"A");
        graph.addEdge(2,3,"B");
        graph.addEdge(3,4,"C");
        graph.addEdge(4,5,"D");
        graph.addEdge(5,1,"E");
        graph.addEdge(4,6,"F");

        return graph;
    }
}
