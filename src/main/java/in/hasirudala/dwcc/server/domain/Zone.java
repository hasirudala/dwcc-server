package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "zones", uniqueConstraints =@UniqueConstraint(columnNames = {"name", "region_id"}))
public class Zone extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    @NotNull
    private Region region;

    @JsonIgnore
    @ManyToMany(mappedBy = "zones")
    private Set<Ward> wards = new HashSet<>();

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Region getRegion() { return region; }

    public void setRegion(Region region) { this.region = region; }

    public Long getRegionId() { return this.region.getId(); }

    public Set<Ward> getWards() {
        return wards;
    }

    public void setWards(Set<Ward> wards) {
        this.wards = wards;
    }
}
