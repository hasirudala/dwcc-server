package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Audited
@Entity
@Table(name = "wards", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "region_id"}))
public class Ward extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    @NotNull
    private Region region;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "wards_zones",
            joinColumns = {@JoinColumn(name = "ward_id")},
            inverseJoinColumns = {@JoinColumn(name = "zone_id")})
    private Set<Zone> zones = new HashSet<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Long getRegionId() {
        return this.region.getId();
    }

    public Set<Zone> getZones() {
        return zones;
    }

    public void setZones(Set<Zone> zones) {
        this.zones = zones;
    }

    public Set<Long> getZoneIds() {
        return zones.stream().map(BaseEntity::getId).collect(Collectors.toSet());
    }
}
