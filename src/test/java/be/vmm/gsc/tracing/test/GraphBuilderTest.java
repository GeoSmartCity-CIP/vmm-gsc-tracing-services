package be.vmm.gsc.tracing.test;

import be.vmm.gsc.tracing.data.dao.IKoppelpuntDao;
import be.vmm.gsc.tracing.data.dao.IRioollinkDao;
import be.vmm.gsc.tracing.data.model.Koppelpunt;
import be.vmm.gsc.tracing.data.model.Rioollink;
import be.vmm.gsc.tracing.graph.IdGraphBuilder;
import org.jgrapht.DirectedGraph;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by s.vanmieghem on 15/12/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:tracing.spring.xml")
public class GraphBuilderTest {

    @Resource
    IKoppelpuntDao koppelpuntDAO;

    @Resource
    IRioollinkDao rioollinkDao;

    @Test
    public void build() {
        long start = System.currentTimeMillis();
        List<Koppelpunt> k = koppelpuntDAO.findAll();
        List<Rioollink> r = rioollinkDao.findAll();
        DirectedGraph g = IdGraphBuilder.build(r, false);
        System.out.println("Total time:"+(System.currentTimeMillis()-start));
    }
}
