package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Audited
@Entity
@Table(name = "expense_entries")
public class ExpenseEntry extends BaseEntity {
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private ExpenseRecord record;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_item_id")
    private ExpenseItem item;

    @Column(name = "num_items")
    private Integer numberOfItems;

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

    public ExpenseItem getItem() {
        return item;
    }

    public void setItem(ExpenseItem item) {
        this.item = item;
    }

    @JsonProperty("expenseItemId")
    public Long getItemId() {
        return this.item.getId();
    }

    @JsonProperty("numItems")
    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
