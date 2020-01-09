package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Audited
@Entity
@Table(name = "outgoing_waste_entries")
public class OutgoingWasteEntry extends BaseEntity {
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private OutgoingWasteRecord record;

    @JsonIgnore
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "outgoing_waste_entry_items",
            joinColumns = {@JoinColumn(name = "entry_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private Set<WasteItem> items = new HashSet<>();

    @Column(nullable = false)
    private Double quantity;

    @Column
    private Double rejectQuantity;

    @Column
    private Double stockInHand;

    @Column
    private Double rate;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private WasteBuyer buyer = null;

    public OutgoingWasteRecord getRecord() {
        return record;
    }

    public void setRecord(OutgoingWasteRecord wasteRecord) {
        this.record = wasteRecord;
    }

    public Long getRecordId() {
        return this.record.getId();
    }

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

    public Double getRejectQuantity() {
        return rejectQuantity;
    }

    public void setRejectQuantity(Double rejectQuantity) {
        this.rejectQuantity = rejectQuantity;
    }

    public Double getStockInHand() {
        return stockInHand;
    }

    public void setStockInHand(Double stockInHand) {
        this.stockInHand = stockInHand;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public WasteBuyer getBuyer() {
        return buyer;
    }

    public void setBuyer(WasteBuyer buyer) {
        this.buyer = buyer;
    }

    public Long getBuyerId() {
        if (this.buyer == null) return null;
        return this.buyer.getId();
    }
}
