package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Audited
@Entity
@Table(name = "expense_records")
public class ExpenseRecord extends BaseEntity {
    @Column(nullable = false)
    @NotNull
    private Date date;

    @JsonManagedReference
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExpenseEntry> entries = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExpensePurchaseEntry> purchaseEntries = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dwcc_id", nullable = false)
    @NotNull
    private Dwcc dwcc;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<ExpenseEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<ExpenseEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(ExpenseEntry entry) {
        this.entries.add(entry);
        entry.setRecord(this);
    }

    public Set<Long> getEntryIds() {
        return entries.stream().map(BaseEntity::getId).collect(Collectors.toSet());
    }

    public Set<ExpensePurchaseEntry> getPurchaseEntries() {
        return purchaseEntries;
    }

    public void setPurchaseEntries(Set<ExpensePurchaseEntry> purchaseEntries) {
        this.purchaseEntries = purchaseEntries;
    }

    public void addPurchaseEntry(ExpensePurchaseEntry entry) {
        this.purchaseEntries.add(entry);
        entry.setRecord(this);
    }

    public Set<Long> getPurchaseEntryIds() {
        return purchaseEntries.stream().map(BaseEntity::getId).collect(Collectors.toSet());
    }

    public Dwcc getDwcc() {
        return dwcc;
    }

    public void setDwcc(Dwcc dwcc) {
        this.dwcc = dwcc;
    }

    public Long getDwccId() {
        return this.dwcc.getId();
    }
}
