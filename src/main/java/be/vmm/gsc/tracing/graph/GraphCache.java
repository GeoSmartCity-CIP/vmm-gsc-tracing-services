package be.vmm.gsc.tracing.graph;

import be.vmm.gsc.tracing.data.dao.IRioollinkDao;
import be.vmm.gsc.tracing.data.model.Rioollink;
import org.jgrapht.DirectedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by s.vanmieghem on 25/08/2016.
 */
@Component
public class GraphCache {
    private final Logger log = LoggerFactory.getLogger(GraphCache.class);

    private DirectedGraph<Integer, Rioollink> upstream;
    private DirectedGraph<Integer, Rioollink> downstream;

    @Autowired
    private IRioollinkDao rioollinkDao;

    public GraphCache() {
    }

    @PostConstruct
    public void init() {
        log.info("Build upstream and downstream network.");
        List<Rioollink> rioollinks = rioollinkDao.findAll();
        upstream = RioollinkGraphBuilder.build(rioollinks, true);
        downstream = RioollinkGraphBuilder.build(rioollinks, false);
    }

    public DirectedGraph<Integer, Rioollink> getUpstream() {
        return upstream;
    }

    public DirectedGraph<Integer, Rioollink> getDownstream() {
        return downstream;
    }
}
