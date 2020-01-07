package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Audited
@Entity
@Table(name = "waste_buyers")
public class WasteBuyer extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    @NotNull
    private Region region;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Column
    private String address;

    @Column
    private Boolean isKspcbAuthorized = false;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "waste_buyers_items",
            joinColumns = {@JoinColumn(name = "buyer_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private Set<WasteItem> acceptedItems = new HashSet<>();

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

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Long getZoneId() {
        if (zone == null) return null;
        return zone.getId();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("isKspcbAuthorized")
    public Boolean getKspcbAuthorized() {
        return isKspcbAuthorized;
    }

    public void setKspcbAuthorized(Boolean kspcbAuthorized) {
        this.isKspcbAuthorized = kspcbAuthorized;
    }

    public Set<WasteItem> getAcceptedItems() {
        return acceptedItems;
    }

    public void setAcceptedItems(Set<WasteItem> acceptedItems) {
        this.acceptedItems = acceptedItems;
    }

    public Set<Long> getAcceptedItemIds() {
        return acceptedItems.stream().map(BaseEntity::getId).collect(Collectors.toSet());
    }
}
