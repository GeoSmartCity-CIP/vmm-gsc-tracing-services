package be.vmm.gsc.tracing.webservice.controller;

import be.vmm.gsc.tracing.data.model.Rioollink;
import be.vmm.gsc.tracing.graph.GraphCache;
import be.vmm.gsc.tracing.service.FeatureService;
import be.vmm.gsc.tracing.service.GeoJSONService;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wololo.geojson.Feature;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tracing")
public class TracingController {

    @Autowired
    private GraphCache cache;

    @Autowired
    private FeatureService featureService;
    @Autowired
    private GeoJSONService geoJSONService;

    @RequestMapping(value = "/{updown}/{id}", method = RequestMethod.GET, produces = "application/json")
    @CrossOrigin("*")
    @ResponseBody
    public String trace(@PathVariable String updown,
                        @PathVariable("id") Integer id) {

        DirectedGraph<Integer, Rioollink> graph;
        if ("downstream".equalsIgnoreCase(updown)) {
            graph = cache.getDownstream();
        } else {
            graph = cache.getUpstream();
        }

        // trace result
        List<Rioollink> rioollinken = new ArrayList<>();

        // graph iteration
        final Graph<Integer, Rioollink> trace = new DefaultDirectedGraph(Rioollink.class);
        BreadthFirstIterator iter = new BreadthFirstIterator<>(graph, id);
        iter.addTraversalListener(new TraversalListenerAdapter() {
            @Override
            public void edgeTraversed(EdgeTraversalEvent event) {
                Rioollink edge = (Rioollink)event.getEdge();
                trace.addVertex(graph.getEdgeSource(edge));
                trace.addVertex(graph.getEdgeTarget(edge));
                trace.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
                rioollinken.add(edge);
            }
        });
        while (iter.hasNext()) {
            iter.next();
        }

        return createJsonResponse(rioollinken).toString();
    }

    private JSONObject createJsonResponse(List<Rioollink> rioollinken) {
        List<Feature> features = featureService.convertRioollinkListToGeojsonFeatureList(rioollinken);
        return geoJSONService.getJsonResponse(features);
    }
}
