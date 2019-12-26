package in.hasirudala.dwcc.server.web.contract;


import java.sql.Date;

public class DwccRequest extends BaseRequest{
    private String name;
    private Long wardId;
    private String ownerOrAgencyName;
    private Boolean isOwnedAndOperated;
    private Date operatingSince;
    private Integer areaInSqFt;
    private Boolean isMouSigned;
    private Boolean isMoaSigned;
    private Date dateMouSigned;
    private Date dateMouExpires;

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

    public Boolean getMouSigned() {
        return isMouSigned;
    }

    public void setMouSigned(Boolean mouSigned) {
        isMouSigned = mouSigned;
    }

    public Boolean getMoaSigned() {
        return isMoaSigned;
    }

    public void setMoaSigned(Boolean moaSigned) {
        isMoaSigned = moaSigned;
    }

    public Date getDateMouSigned() {
        return dateMouSigned;
    }

    public void setDateMouSigned(Date dateMouSigned) {
        this.dateMouSigned = dateMouSigned;
    }

    public Date getDateMouExpires() {
        return dateMouExpires;
    }

    public void setDateMouExpires(Date dateMouExpires) {
        this.dateMouExpires = dateMouExpires;
    }
}
