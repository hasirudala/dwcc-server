package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Entity
@Table(name = "outgoing_waste_items")
public class OutgoingWasteItem extends BaseEntity {
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private WasteItem item;

    @Column(nullable = false)
    private Double quantity;

    @Column
    private Double rate;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private WasteBuyer buyer = null;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private OutgoingWasteRecord record;

    public WasteItem getItem() {
        return item;
    }

    public void setItem(WasteItem item) {
        this.item = item;
    }

    public Long getItemId() {
        return this.item.getId();
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

    public WasteBuyer getBuyer() {
        return buyer;
    }

    public void setBuyer(WasteBuyer buyer) {
        this.buyer = buyer;
    }

    public OutgoingWasteRecord getRecord() {
        return record;
    }

    public void setRecord(OutgoingWasteRecord wasteRecord) {
        this.record = wasteRecord;
    }

    public Long getRecordId() {
        return this.record.getId();
    }

    public Long getBuyerId() {
        if (this.buyer == null) return null;
        return this.buyer.getId();
    }
}
