package be.vmm.gsc.tracing.service;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.wololo.geojson.Feature;
import org.wololo.geojson.GeoJSON;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.List;

@Component
public class GeoJSONService {

    public JSONObject getJsonResponse(List<Feature> features){
        GeoJSONWriter writer = new GeoJSONWriter();
        GeoJSON json = writer.write(features);
        JSONObject jsonObject = new JSONObject(json.toString());
        jsonObject.put("crs", createCRSJSONObject());
        return jsonObject;
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
