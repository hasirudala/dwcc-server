package in.hasirudala.dwcc.server.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Entity
@Table(name = "regions")
public class Region extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
