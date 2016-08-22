package be.vmm.gsc.tracing.webservice.controller;

import be.vmm.gsc.tracing.data.dao.IRioollinkDao;
import be.vmm.gsc.tracing.data.model.Rioollink;
import be.vmm.gsc.tracing.graph.IdGraphBuilder;
import be.vmm.gsc.tracing.service.FeatureService;
import be.vmm.gsc.tracing.service.GeoJSONService;
import org.jgrapht.Graph;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wololo.geojson.Feature;
import org.wololo.geojson.GeoJSON;
import org.wololo.jts2geojson.GeoJSONWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tracing")
public class TracingController {

    @Autowired
    private IRioollinkDao rioollinkDao;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private GeoJSONService geoJSONService;

    @GET
    @RequestMapping(value = "/{reverse}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @CrossOrigin("*")
    public String trace(@PathVariable Boolean reverse,
                        @PathVariable("id") Integer id) {
        List<Rioollink> rioollinks = rioollinkDao.findAllIdsOnly();

        final Graph<Integer, Integer> g = IdGraphBuilder.build(rioollinks, reverse);
        final Graph<Integer, Integer> trace = new DefaultDirectedGraph<>(Integer.class);
        final List<Integer> rioollinkIds = new ArrayList<>();
        BreadthFirstIterator iter = new BreadthFirstIterator<>(g, id);
        iter.addTraversalListener(new TraversalListenerAdapter() {
            @Override
            public void edgeTraversed(EdgeTraversalEvent event) {
                Integer edge = (Integer) event.getEdge();
                trace.addVertex(g.getEdgeSource(edge));
                trace.addVertex(g.getEdgeTarget(edge));
                trace.addEdge(g.getEdgeSource(edge), g.getEdgeTarget(edge), edge);
                rioollinkIds.add(edge);
            }
        });

        while (iter.hasNext()) {
            iter.next();
        }
        List<Rioollink> rioollinken = rioollinkDao.findAll(rioollinkIds);
        return createJsonResponse(rioollinken).toString();
    }

    private JSONObject createJsonResponse(List<Rioollink> rioollinken) {
        List<Feature> features = featureService.convertRioollinkListToGeojsonFeatureList(rioollinken);
        return geoJSONService.getJsonResponse(features);
    }
}



