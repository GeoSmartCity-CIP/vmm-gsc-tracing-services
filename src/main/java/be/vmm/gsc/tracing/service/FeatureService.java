package be.vmm.gsc.tracing.service;

import be.vmm.gsc.tracing.data.model.Rioollink;
import com.vividsolutions.jts.geom.Coordinate;
import org.springframework.stereotype.Component;
import org.wololo.geojson.Feature;
import org.wololo.geojson.Geometry;
import org.wololo.geojson.LineString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FeatureService {

    public List<Feature> convertRioollinkListToGeojsonFeatureList(List<Rioollink> rioollinken) {
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

}
