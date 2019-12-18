package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Entity
@Table(name = "incoming_waste_items")
public class IncomingWasteItem extends BaseEntity {
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private WasteItem item;

    @Column(nullable = false)
    private Double quantity;

    @Column
    private Double rejectQuantity;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private IncomingWasteRecord record;

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

    public Double getRejectQuantity() {
        return rejectQuantity;
    }

    public void setRejectQuantity(Double rejectQuantity) {
        this.rejectQuantity = rejectQuantity;
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
