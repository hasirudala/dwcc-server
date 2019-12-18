package in.hasirudala.dwcc.server.domain;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Audited
@Entity
@Table(name = "vehicle_types")
public class VehicleType extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
