package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Audited
@Entity
@Table(name = "incoming_waste_mixed")
public class IncomingMixedWaste extends BaseEntity {
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "incoming_waste_mixed_items",
            joinColumns = {@JoinColumn(name = "mixed_waste_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private Set<WasteItem> items = new HashSet<>();

    @Column(nullable = false)
    private Double quantity;

    @Column
    private Double rate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private IncomingWasteRecord record;

    public Set<WasteItem> getItems() {
        return items;
    }

    public void setItems(Set<WasteItem> items) {
        this.items = items;
    }

    public Set<Long> getItemIds() {
        return items.stream().map(WasteItem::getId).collect(Collectors.toSet());
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public IncomingWasteRecord getRecord() {
        return record;
    }

    public void setRecord(IncomingWasteRecord wasteRecord) {
        this.record = wasteRecord;
    }

    public Long getRecordId() {
        return this.record.getId();
    }
}
