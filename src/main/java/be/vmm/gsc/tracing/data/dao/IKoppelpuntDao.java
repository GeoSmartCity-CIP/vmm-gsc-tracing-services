package be.vmm.gsc.tracing.data.dao;

import be.vmm.gsc.tracing.data.model.Koppelpunt;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by s.vanmieghem on 15/12/2015.
 */
public interface IKoppelpuntDao extends JpaRepository<Koppelpunt, Integer> {
}
