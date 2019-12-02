package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Audited
@Entity
@Table(name = "dwccs", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "ward_id"}))
public class Dwcc extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    @NotNull
    private Ward ward;

    @Column(name = "owner_or_agency_name")
    private String ownerOrAgencyName;

    @Column(name = "is_owned_and_operated")
    private Boolean isOwnedAndOperated;

    @Column(name = "date_operating_since")
    private Date operatingSince;

    @Column(name = "area_in_sqft")
    private Integer areaInSqFt;

    @Column(name = "is_mou_moa_signed")
    private Boolean isMouMoaSigned;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public String getOwnerOrAgencyName() {
        return ownerOrAgencyName;
    }

    public void setOwnerOrAgencyName(String ownerOrAgencyName) {
        this.ownerOrAgencyName = ownerOrAgencyName;
    }

    public Boolean getOwnedAndOperated() {
        return isOwnedAndOperated;
    }

    public void setOwnedAndOperated(Boolean ownedAndOperated) {
        isOwnedAndOperated = ownedAndOperated;
    }

    public Date getOperatingSince() {
        return operatingSince;
    }

    public void setOperatingSince(Date operatingSince) {
        this.operatingSince = operatingSince;
    }

    public Integer getAreaInSqFt() {
        return areaInSqFt;
    }

    public void setAreaInSqFt(Integer areaInSqFt) {
        this.areaInSqFt = areaInSqFt;
    }

    public Boolean getMouMoaSigned() {
        return isMouMoaSigned;
    }

    public void setMouMoaSigned(Boolean mouMoaSigned) {
        isMouMoaSigned = mouMoaSigned;
    }

    public Long getWardId() { return this.ward.getId(); }
}
