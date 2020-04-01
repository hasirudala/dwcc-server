package in.hasirudala.dwcc.server.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Entity
@Table(name = "waste_sources")
public class WasteSource extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
