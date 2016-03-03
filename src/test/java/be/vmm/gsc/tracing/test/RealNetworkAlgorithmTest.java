package be.vmm.gsc.tracing.test;

import be.vmm.gsc.tracing.data.dao.IKoppelpuntDao;
import be.vmm.gsc.tracing.data.dao.IRioollinkDao;
import be.vmm.gsc.tracing.data.model.Koppelpunt;
import be.vmm.gsc.tracing.data.model.Rioollink;
import be.vmm.gsc.tracing.graph.IdGraphBuilder;
import be.vmm.gsc.tracing.graph.RioollinkGraphBuilder;
import be.vmm.gsc.tracing.util.Timer;
import org.jgrapht.Graph;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s.vanmieghem on 15/12/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:tracing.spring.xml")
public class RealNetworkAlgorithmTest {

    @Resource
    IKoppelpuntDao koppelpuntDAO;

    @Resource
    IRioollinkDao rioollinkDao;

    @Test
    public void BreadthFirstIterateIdGraph() {
        Timer t = new Timer().start();
        t.logDelta("Start");

        List<Rioollink> r = rioollinkDao.findAll();
        t.logDelta("Data fetched");

        final Graph<Integer, Integer> g = IdGraphBuilder.build(r, false);
        t.logDelta("Graph ready");

        final Graph<Integer, Integer> trace = new DefaultDirectedGraph<Integer, Integer>(Integer.class);
        final List<Integer> rioollinkIds = new ArrayList<Integer>();

        BreadthFirstIterator iter = new BreadthFirstIterator<Integer, Integer>(g, 442221);
        iter.addTraversalListener(new TraversalListenerAdapter(){
            @Override
            public void edgeTraversed(EdgeTraversalEvent event) {
                Integer edge = (Integer)event.getEdge();
                trace.addVertex(g.getEdgeSource(edge));
                trace.addVertex(g.getEdgeTarget(edge));
                trace.addEdge(g.getEdgeSource(edge), g.getEdgeTarget(edge), edge);
                rioollinkIds.add(edge);
            }
        });

        while (iter.hasNext()) {
            iter.next();
        }
        t.logDelta("Trace complete");

        List<Rioollink> rioollinken = rioollinkDao.findAll(rioollinkIds);
        t.logDelta("Trace data fetched");

        for (Rioollink rio : rioollinken) {
            System.out.println(rio.id+",");
        }
        t.logDelta("Done");
    }

    @Test
    public void BreadthFirstIterateIdGraphReverse() {
        Timer t = new Timer().start();
       t.logDelta("Start");

        List<Rioollink> r = rioollinkDao.findAllIdsOnly();
        t.logDelta("Data fetched");

        final Graph<Integer, Integer> g = IdGraphBuilder.build(r, true);
        t.logDelta("Graph ready");

        final Graph<Integer, Integer> trace = new DefaultDirectedGraph<Integer, Integer>(Integer.class);
        final List<Integer> rioollinkIds = new ArrayList<Integer>();

        BreadthFirstIterator iter = new BreadthFirstIterator<Integer, Integer>(g, 136558);
        iter.addTraversalListener(new TraversalListenerAdapter(){
            @Override
            public void edgeTraversed(EdgeTraversalEvent event) {
                Integer edge = (Integer)event.getEdge();
                trace.addVertex(g.getEdgeSource(edge));
                trace.addVertex(g.getEdgeTarget(edge));
                trace.addEdge(g.getEdgeSource(edge), g.getEdgeTarget(edge), edge);
                rioollinkIds.add(edge);
            }
        });

        while (iter.hasNext()) {
            iter.next();
        }
        t.logDelta("Trace complete");

        List<Rioollink> rioollinken = rioollinkDao.findAll(rioollinkIds);
        t.logDelta("Trace data fetched");

        for (Rioollink rio : rioollinken) {
            System.out.println(rio.id+",");
        }
        t.logDelta("Done");
    }

    @Test
    public void BreadthFirstIterateRioollinkGraphReverse() {
        Timer t = new Timer().start();
        t.logDelta("Start");

        List<Rioollink> r = rioollinkDao.findAll();
        t.logDelta("Data fetched");

        final Graph<Integer, Rioollink> g = RioollinkGraphBuilder.build(r, true);
        t.logDelta("Graph ready");

        final List<Rioollink> trace = new ArrayList<Rioollink>();

        BreadthFirstIterator iter = new BreadthFirstIterator<Integer, Rioollink>(g, 136558);
        iter.addTraversalListener(new TraversalListenerAdapter(){
            @Override
            public void edgeTraversed(EdgeTraversalEvent event) {
                trace.add((Rioollink)event.getEdge());
            }
        });

        while (iter.hasNext()) {
            iter.next();
        }
        t.logDelta("Trace complete");

        for (Rioollink rio : trace) {
            System.out.println(rio.id+","+rio.geometry.toText());
        }
        t.logDelta("Done");
    }
}
