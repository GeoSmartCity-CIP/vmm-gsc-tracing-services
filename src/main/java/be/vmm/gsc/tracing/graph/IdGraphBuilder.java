package be.vmm.gsc.tracing.graph;

import be.vmm.gsc.tracing.data.model.Koppelpunt;
import be.vmm.gsc.tracing.data.model.Rioollink;
import be.vmm.gsc.tracing.util.Timer;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * Graph filled with object identifiers (database id) only.
 * Most generic solution. Query database with specific filters, build graph with id's,
 * perform trace and collect data as needed or map id to an already existing model.
 *
 * Created by s.vanmieghem on 15/12/2015.
 */
@Component
public class IdGraphBuilder {

    /**
     * @param links
     * @param reverse true: edge from end to start (downstream); false: edge from start to end (upstream)
     * @return a graph with identifiers for nodes (koppelpunten) and edges (rioollinken); unlinked nodes not retained in the graph
     */
    public static DirectedGraph<Integer, Integer> build(List<Rioollink> links, boolean reverse) {
        DefaultDirectedGraph<Integer, Integer> graph = new DefaultDirectedGraph<Integer, Integer>(Integer.class);
        for (Rioollink l : links) {
            graph.addVertex(l.startKoppelpuntId);
            graph.addVertex(l.eindKoppelpuntId);
            if (reverse) {
                graph.addEdge(l.eindKoppelpuntId, l.startKoppelpuntId, l.id);
            } else {
                graph.addEdge(l.startKoppelpuntId, l.eindKoppelpuntId, l.id);
            }
        }
        return graph;
    }



}