package be.vmm.gsc.tracing.data.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by s.vanmieghem on 15/12/2015.
 */
@Entity
@Table(name="koppelpunt")
public class Koppelpunt {

    @Id
    public Integer id;

}
