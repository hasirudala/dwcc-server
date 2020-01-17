package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Audited
@Entity
@Table(name = "expense_purchase_entries")
public class ExpensePurchaseEntry extends BaseEntity {
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private ExpenseRecord record;

    @JsonIgnore
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "expense_purchase_entry_items",
            joinColumns = {@JoinColumn(name = "purchase_entry_id")},
            inverseJoinColumns = {@JoinColumn(name = "waste_item_id")})
    private Set<WasteItem> items = new HashSet<>();

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

    public Set<WasteItem> getItems() {
        return items;
    }

    public void setItems(Set<WasteItem> items) {
        this.items = items;
    }

    @JsonProperty("wasteItemIds")
    public Set<Long> getItemIds() {
        return items.stream().map(WasteItem::getId).collect(Collectors.toSet());
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
