package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "incoming_waste_records")
public class IncomingWasteRecord extends BaseEntity {
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
    private Set<IncomingDtdWasteEntry> dtdCollection = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IncomingMixedWasteEntry> mixedWaste = new HashSet<>();

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

    public Set<IncomingDtdWasteEntry> getDtdCollection() {
        return dtdCollection;
    }

    public void setDtdCollection(Set<IncomingDtdWasteEntry> dtdCollection) {
        this.dtdCollection = dtdCollection;
    }

    public void addDtdWaste(IncomingDtdWasteEntry dtdWaste) {
        this.dtdCollection.add(dtdWaste);
        dtdWaste.setRecord(this);
    }

    public Set<IncomingMixedWasteEntry> getMixedWaste() {
        return mixedWaste;
    }

    public void setMixedWaste(Set<IncomingMixedWasteEntry> mixedWaste) {
        this.mixedWaste = mixedWaste;
    }

    public void addMixedWaste(IncomingMixedWasteEntry mixedWasteItem) {
        this.mixedWaste.add(mixedWasteItem);
        mixedWasteItem.setRecord(this);
    }
}
