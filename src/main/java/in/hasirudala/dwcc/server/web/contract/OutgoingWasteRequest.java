package in.hasirudala.dwcc.server.web.contract;

import java.sql.Date;
import java.util.List;

public class OutgoingWasteRequest extends BaseRequest {
    private Date fromDate;
    private Date toDate;
    private Double rejectQuantity;
    private Double sanitaryQuantity;
    private Long dwccId;
    private List<OutgoingWasteEntryRequest> entries;
    private String note;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Double getRejectQuantity() {
        return rejectQuantity;
    }

    public void setRejectQuantity(Double rejectQuantity) {
        this.rejectQuantity = rejectQuantity;
    }

    public Double getSanitaryQuantity() {
        return sanitaryQuantity;
    }

    public void setSanitaryQuantity(Double sanitaryWasteQuantity) {
        this.sanitaryQuantity = sanitaryWasteQuantity;
    }

    public Long getDwccId() {
        return dwccId;
    }

    public void setDwccId(Long dwccId) {
        this.dwccId = dwccId;
    }

    public List<OutgoingWasteEntryRequest> getEntries() {
        return entries;
    }

    public void setEntries(List<OutgoingWasteEntryRequest> entries) {
        this.entries = entries;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

