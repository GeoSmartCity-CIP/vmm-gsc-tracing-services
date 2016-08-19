package be.vmm.gsc.tracing.webservice.controller;

import be.vmm.gsc.tracing.data.dao.IRioollinkDao;
import be.vmm.gsc.tracing.data.model.Rioollink;
import be.vmm.gsc.tracing.graph.IdGraphBuilder;
import com.vividsolutions.jts.geom.Coordinate;
import org.jgrapht.Graph;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GET
    @RequestMapping(value = "/{reverse}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String trace(@PathVariable Boolean reverse,
                        @PathVariable("id") Integer id) {
        System.out.println(reverse + "---" + id);
        List<Rioollink> rioollinks = rioollinkDao.findAllIdsOnly();

        final Graph<Integer, Integer> g = IdGraphBuilder.build(rioollinks, reverse);
        final Graph<Integer, Integer> trace = new DefaultDirectedGraph<Integer, Integer>(Integer.class);
        final List<Integer> rioollinkIds = new ArrayList<Integer>();
        BreadthFirstIterator iter = new BreadthFirstIterator<Integer, Integer>(g, id);
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
        JSONObject crsProperties = new JSONObject();
        crsProperties.put("name", "urn:ogc:def:crs:EPSG::31370");

        JSONObject crs = new JSONObject();
        crs.put("type", "name");
        crs.put("properties", crsProperties);

        JSONObject jo = new JSONObject();
        jo.put("type", "FeatureCollection");
        jo.put("crs", crs);
        jo.put("features", featureJsonArray(rioollinken));
        return jo;
    }

    private JSONArray featureJsonArray(List<Rioollink> rioollinken) {
        JSONArray arr = new JSONArray();
        for (Rioollink rioollink : rioollinken) {
            arr.put(featuresJson(rioollink));
        }
        return arr;
    }

    private JSONObject featuresJson(Rioollink rioollink) {

        JSONObject geomjson = new JSONObject();
        geomjson.put("type", "LineString");
        geomjson.put("coordinated", coordinateArray(rioollink.geometry.getCoordinates()));

        JSONObject props = new JSONObject();
        props.put("id", rioollink.id);

        JSONObject jo = new JSONObject();
        jo.put("type", "Feature");
        jo.put("geom", geomjson);
        jo.put("properties", props);

        return jo;
    }

    private String coordinateArray(Coordinate[] coordinates) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Coordinate c : coordinates) {
            sb.append("[").append(c.getOrdinate(Coordinate.X)).append(",").append(c.getOrdinate(Coordinate.Y)).append("]");
        }
        sb.append("]");
        return sb.toString();
    }
}


