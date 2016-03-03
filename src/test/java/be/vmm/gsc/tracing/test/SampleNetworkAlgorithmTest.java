package be.vmm.gsc.tracing.test;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.junit.Test;

import java.util.List;

/**
 * Created by s.vanmieghem on 15/12/2015.
 */
public class SampleNetworkAlgorithmTest {

    @Test
    public void FloydWarshallShortestPaths() {
        List<GraphPath<Integer, String>> result = new FloydWarshallShortestPaths<Integer, String>(SampleNetwork.boomStroomafwaarts()).getShortestPaths(1);
        for (GraphPath gp : result) {
            System.out.println(gp.toString());
        }

        List<GraphPath<Integer, String>> result2 = new FloydWarshallShortestPaths<Integer, String>(SampleNetwork.dubbel()).getShortestPaths(1);
        for (GraphPath gp : result2) {
            System.out.println(gp.toString());
        }
    }

    @Test
    public void BreadthFirstIterate() {
        BreadthFirstIterator iter = new BreadthFirstIterator<Integer, String>(SampleNetwork.boomStroomafwaarts(), 1);
        iter.addTraversalListener(new TraversalListenerAdapter(){
            @Override
            public void edgeTraversed(EdgeTraversalEvent e) {
                System.out.println(e.getEdge());
            }
        });

        while (iter.hasNext()) {
            iter.next();
        }
    }
    @Test
    public void BreadthFirstIterateDirected() {
        BreadthFirstIterator iter = new BreadthFirstIterator<Integer, String>(SampleNetwork.boomStroomopwaarts(), 1);
        iter.addTraversalListener(new TraversalListenerAdapter(){
            @Override
            public void edgeTraversed(EdgeTraversalEvent e) {
                System.out.println(e.getEdge());
            }
        });

        while (iter.hasNext()) {
            iter.next();
        }
    }
    @Test
    public void BreadthFirstIterateLoop() {
        BreadthFirstIterator iter = new BreadthFirstIterator<Integer, String>(SampleNetwork.lus(), 1);
        iter.addTraversalListener(new TraversalListenerAdapter(){
            @Override
            public void edgeTraversed(EdgeTraversalEvent e) {
                System.out.println(e.getEdge());
            }
        });

        while (iter.hasNext()) {
            iter.next();
        }
    }


}
