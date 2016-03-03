package be.vmm.gsc.tracing.test;

import be.vmm.gsc.tracing.data.dao.IKoppelpuntDao;
import be.vmm.gsc.tracing.data.dao.IRioollinkDao;
import be.vmm.gsc.tracing.data.model.Koppelpunt;
import be.vmm.gsc.tracing.data.model.Rioollink;
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
public class DaoTest {

    @Resource
    IKoppelpuntDao koppelpuntDAO;

    @Resource
    IRioollinkDao rioollinkDao;

    @Test
    public void getKoppelpunt() {
        Koppelpunt k = koppelpuntDAO.findOne(10);
    }

    @Test
    public void getAllKoppelpunten() {
        List<Koppelpunt> k = koppelpuntDAO.findAll();
    }

    @Test
    public void getRioollink() {
        Rioollink r = rioollinkDao.findOne(554985);
    }

    @Test
    public void getAllRioollinken() {
        List<Rioollink> r = rioollinkDao.findAll();
    }


}
