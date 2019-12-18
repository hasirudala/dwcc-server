package in.hasirudala.dwcc.server.web.contract;

import java.sql.Date;
import java.util.List;

public class IncomingWasteRequest extends BaseRequest {
    private Date date;
    private Long dwccId;
    private List<IncomingDtdWasteRequest> dtdCollection;
    private List<IncomingWasteItemRequest> wasteItems;
    private List<IncomingMixedWasteRequest> mixedWaste;
    private Boolean errorsIgnored;
    private Boolean approvedByAdmin;
    private String note;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getDwccId() {
        return dwccId;
    }

    public void setDwccId(Long dwccId) {
        this.dwccId = dwccId;
    }

    public List<IncomingDtdWasteRequest> getDtdCollection() {
        return dtdCollection;
    }

    public void setDtdCollection(List<IncomingDtdWasteRequest> dtdCollection) {
        this.dtdCollection = dtdCollection;
    }

    public List<IncomingWasteItemRequest> getWasteItems() {
        return wasteItems;
    }

    public void setWasteItems(List<IncomingWasteItemRequest> wasteItems) {
        this.wasteItems = wasteItems;
    }

    public List<IncomingMixedWasteRequest> getMixedWaste() {
        return mixedWaste;
    }

    public void setMixedWaste(List<IncomingMixedWasteRequest> mixedWaste) {
        this.mixedWaste = mixedWaste;
    }

    public Boolean isErrorsIgnored() {
        return errorsIgnored;
    }

    public void setErrorsIgnored(Boolean errorsIgnored) {
        this.errorsIgnored = errorsIgnored;
    }

    public Boolean isApprovedByAdmin() {
        return approvedByAdmin;
    }

    public void setApprovedByAdmin(Boolean approvedByAdmin) {
        this.approvedByAdmin = approvedByAdmin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

