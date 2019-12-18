package in.hasirudala.dwcc.server.web.contract;


import java.sql.Date;

public class DwccRequest extends BaseRequest{
    private String name;
    private Long wardId;
    private String ownerOrAgencyName;
    private Boolean isOwnedAndOperated;
    private Date operatingSince;
    private Integer areaInSqFt;
    private Boolean isMouMoaSigned;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWardId() {
        return wardId;
    }

    public void setWardId(Long wardId) {
        this.wardId = wardId;
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
}
