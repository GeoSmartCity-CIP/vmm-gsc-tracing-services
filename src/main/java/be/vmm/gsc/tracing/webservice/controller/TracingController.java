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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wololo.geojson.Feature;
import org.wololo.geojson.GeoJSON;
import org.wololo.geojson.Geometry;
import org.wololo.geojson.LineString;
import org.wololo.jts2geojson.GeoJSONWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return createJsonResponse(rioollinken);
    }

    private String createJsonResponse(List<Rioollink> rioollinken) {
        GeoJSONWriter writer = new GeoJSONWriter();
        GeoJSON json = writer.write(convertRioollinkListToGeojsonFeatureList(rioollinken));
        JSONObject jsonObject = new JSONObject(json.toString());
        jsonObject.put("crs", createCRSJSONObject());
        return jsonObject.toString();
    }

    private List<Feature> convertRioollinkListToGeojsonFeatureList(List<Rioollink> rioollinken) {
        List<Feature> features = new ArrayList<>();
        for (Rioollink rioollink : rioollinken) {
            Map<String, Object> properties = new HashMap<>();
            properties.put("id", rioollink.id);
            features.add(new Feature(convertCoordinateArrayToGeojsonGeometry(rioollink.geometry.getCoordinates()), properties));
        }
        return features;
    }

    private Geometry convertCoordinateArrayToGeojsonGeometry(Coordinate[] coordinates) {
        double[][] multiCoords = new double[coordinates.length][2];
        for (int i = 0; i < coordinates.length; i++) {
            multiCoords[i][0] = coordinates[i].x;
            multiCoords[i][1] = coordinates[i].y;
        }
        return new LineString(multiCoords);
    }

    private JSONObject createCRSJSONObject() {
        JSONObject crsProperties = new JSONObject();
        crsProperties.put("name", "urn:ogc:def:crs:EPSG::31370");

        JSONObject crs = new JSONObject();
        crs.put("type", "name");
        crs.put("properties", crsProperties);
        return crs;
    }
}



