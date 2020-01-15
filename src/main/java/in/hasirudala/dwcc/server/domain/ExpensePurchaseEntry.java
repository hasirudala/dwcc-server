package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Entity
@Table(name = "expense_purchase_entries")
public class ExpensePurchaseEntry extends BaseEntity {
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private ExpenseRecord record;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_item_id")
    private WasteItem item;

    @Column
    private Double amount;

    public ExpenseRecord getRecord() {
        return record;
    }

    public void setRecord(ExpenseRecord expenseRecord) {
        this.record = expenseRecord;
    }

    public Long getRecordId() {
        return this.record.getId();
    }

    public WasteItem getItem() {
        return item;
    }

    public void setItem(WasteItem item) {
        this.item = item;
    }

    @JsonProperty("wasteItemId")
    public Long getItemId() {
        return this.item.getId();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
