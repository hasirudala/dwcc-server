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

    /*
    @Column
    private Boolean errorsIgnored = false;

    @Column
    private Boolean approvedByAdmin = false;
    */

    @Column(name = "notes")
    private String note;

    @JsonManagedReference
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IncomingDtdWaste> dtdCollection = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IncomingWasteItem> wasteItems = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IncomingMixedWaste> mixedWaste = new HashSet<>();

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

    /*
    public Boolean getErrorsIgnored() {
        return errorsIgnored;
    }

    public void setErrorsIgnored(Boolean errorsIgnored) {
        this.errorsIgnored = errorsIgnored;
    }

    public Boolean getApprovedByAdmin() {
        return approvedByAdmin;
    }

    public void setApprovedByAdmin(Boolean approvedByAdmin) {
        this.approvedByAdmin = approvedByAdmin;
    }
    */

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<IncomingDtdWaste> getDtdCollection() {
        return dtdCollection;
    }

    public void setDtdCollection(Set<IncomingDtdWaste> dtdCollection) {
        this.dtdCollection = dtdCollection;
    }

    public void addDtdWaste(IncomingDtdWaste dtdWaste) {
        this.dtdCollection.add(dtdWaste);
        dtdWaste.setRecord(this);
    }

    public Set<IncomingWasteItem> getWasteItems() {
        return wasteItems;
    }

    public void setWasteItems(Set<IncomingWasteItem> wasteItems) {
        this.wasteItems = wasteItems;
    }

    public void addWasteItem(IncomingWasteItem wasteItem) {
        this.wasteItems.add(wasteItem);
        wasteItem.setRecord(this);
    }

    public Set<IncomingMixedWaste> getMixedWaste() {
        return mixedWaste;
    }

    public void setMixedWaste(Set<IncomingMixedWaste> mixedWaste) {
        this.mixedWaste = mixedWaste;
    }

    public void addMixedWaste(IncomingMixedWaste mixedWasteItem) {
        this.mixedWaste.add(mixedWasteItem);
        mixedWasteItem.setRecord(this);
    }
}
