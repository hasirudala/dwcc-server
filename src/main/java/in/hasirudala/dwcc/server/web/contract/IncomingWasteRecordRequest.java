package in.hasirudala.dwcc.server.web.contract;

import java.sql.Date;
import java.util.List;

public class IncomingWasteRecordRequest extends BaseRequest {
    private Date date;
    private Long dwccId;
    private List<IncomingDtdWasteRequest> dtdCollection;
    private List<IncomingMixedWasteRequest> mixedWaste;
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

    public List<IncomingMixedWasteRequest> getMixedWaste() {
        return mixedWaste;
    }

    public void setMixedWaste(List<IncomingMixedWasteRequest> mixedWaste) {
        this.mixedWaste = mixedWaste;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

