package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Audited
@Entity
@Table(name = "incoming_waste_entries_mixed")
public class IncomingMixedWasteEntry extends BaseEntity {
    @JsonIgnore
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "incoming_waste_entries_mixed_items",
            joinColumns = {@JoinColumn(name = "entry_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private Set<WasteItem> items = new HashSet<>();

    @Column(name = "total_kgs", nullable = false)
    private Double quantity;

    @Column(name = "reject_kgs", nullable = false)
    private Double rejectQty;

    @Column(name = "rate_per_kg")
    private Double rate;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "source_id")
    private WasteSource sourceOfWaste;

    @Column
    private String billNumber;

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

    public Double getRejectQty() {
        return rejectQty;
    }

    public void setRejectQty(Double rejectQty) {
        this.rejectQty = rejectQty;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public WasteSource getSourceOfWaste() {
        return sourceOfWaste;
    }

    public void setSourceOfWaste(WasteSource sourceOfWaste) {
        this.sourceOfWaste = sourceOfWaste;
    }

    public Long getSourceId() {
        return this.sourceOfWaste.getId();
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
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
