package in.hasirudala.dwcc.server.web.contract;

import java.sql.Date;
import java.util.List;

public class OutgoingWasteRequest extends BaseRequest {
    private Date date;
    private Long dwccId;
    private List<OutgoingWasteItemRequest> wasteItems;
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

    public List<OutgoingWasteItemRequest> getWasteItems() {
        return wasteItems;
    }

    public void setWasteItems(List<OutgoingWasteItemRequest> wasteItems) {
        this.wasteItems = wasteItems;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

