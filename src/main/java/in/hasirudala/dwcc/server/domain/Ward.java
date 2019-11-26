package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Audited
@Entity
@Table(name = "wards", uniqueConstraints =@UniqueConstraint(columnNames = {"name", "region_id"}))
public class Ward extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    @NotNull
    private Region region;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @JsonIgnore
    public Region getRegion() { return region; }

    public void setRegion(Region region) { this.region = region; }

    public Long getRegionId() { return this.region.getId(); }
}
