package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "outgoing_waste_records")
public class OutgoingWasteRecord extends BaseEntity {
    @Column(nullable = false)
    @NotNull
    private Date date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dwcc_id", nullable = false)
    @NotNull
    private Dwcc dwcc;

    @Column(name = "notes")
    private String note;

    @JsonManagedReference
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OutgoingWasteItem> wasteItems = new HashSet<>();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<OutgoingWasteItem> getWasteItems() {
        return wasteItems;
    }

    public void setWasteItems(Set<OutgoingWasteItem> wasteItems) {
        this.wasteItems = wasteItems;
    }

    public void addWasteItem(OutgoingWasteItem wasteItem) {
        this.wasteItems.add(wasteItem);
        wasteItem.setRecord(this);
    }
}
