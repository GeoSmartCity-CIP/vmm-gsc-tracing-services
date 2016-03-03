package be.vmm.gsc.tracing.data.dao;

import be.vmm.gsc.tracing.data.model.Rioollink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by s.vanmieghem on 15/12/2015.
 */
public interface IRioollinkDao extends JpaRepository<Rioollink, Integer> {

    @Query("select new Rioollink(r.id, r.startKoppelpuntId, r.eindKoppelpuntId) from Rioollink r")
    List<Rioollink> findAllIdsOnly();
}
