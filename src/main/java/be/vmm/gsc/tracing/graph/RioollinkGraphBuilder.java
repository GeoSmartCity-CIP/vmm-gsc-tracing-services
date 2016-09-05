package be.vmm.gsc.tracing.graph;

import be.vmm.gsc.tracing.data.model.Rioollink;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.List;

/**
 * cf IdGraphBuilder.
 * This one contains the basic server pipe information, not only its identifier.
 *
 * Created by s.vanmieghem on 16/12/2015.
 */
public class RioollinkGraphBuilder {
    /**
     * @param links
     * @param reverse true: edge from end to start (upstream); false: edge from start to end (downstream)
     * @return a graph with identifiers for nodes (koppelpunten) and edges (rioollinken); unlinked nodes not retained in the graph
     */
    public static DirectedGraph<Integer, Rioollink> build(List<Rioollink> links, boolean reverse) {
        DefaultDirectedGraph<Integer, Rioollink> graph = new DefaultDirectedGraph<Integer, Rioollink>(Rioollink.class);
        for (Rioollink l : links) {
            graph.addVertex(l.startKoppelpuntId);
            graph.addVertex(l.eindKoppelpuntId);
            if (reverse) {
                graph.addEdge(l.eindKoppelpuntId, l.startKoppelpuntId, l);
            } else {
                graph.addEdge(l.startKoppelpuntId, l.eindKoppelpuntId, l);
            }
        }
        return graph;
    }

}
