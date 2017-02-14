package be.vmm.gsc.tracing.data.model;

import com.vividsolutions.jts.geom.LineString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by s.vanmieghem on 15/12/2015.
 */
@Entity
@Table(name="rioollink")
public class Rioollink {

    public Rioollink() {
    }

    public Rioollink(Integer id, Integer startKoppelpuntId, Integer eindKoppelpuntId) {
        this.id = id;
        this.startKoppelpuntId = startKoppelpuntId;
        this.eindKoppelpuntId = eindKoppelpuntId;
    }

    @Id
    public Integer id;
    public Date beginlifespanversion;
    public Date endlifespanversion;
    @Column(name="rioollinktypeid")
    public Integer rioollinkTypeId;
    @Column(name="sewerwatertypeid")
    public Integer rioolwaterTypeId;

    @Transient
    public Integer ie = 1; // fictief

    @Column(name="startkoppelpuntid")
    public Integer startKoppelpuntId;
    @Column(name="endkoppelpuntid")
    public Integer eindKoppelpuntId;

    @Type(type = "org.hibernate.spatial.GeometryType")
    @Column(name="geom")
    public LineString geometry;
}
