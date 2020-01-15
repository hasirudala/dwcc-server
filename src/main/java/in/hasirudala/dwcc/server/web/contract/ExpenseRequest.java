package in.hasirudala.dwcc.server.web.contract;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRequest extends BaseRequest {
    private Date date;
    private Long dwccId;
    private List<ExpenseEntryRequest> entries = new ArrayList<>();
    private List<ExpensePurchaseEntryRequest> purchaseEntries = new ArrayList<>();

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

    public List<ExpenseEntryRequest> getEntries() {
        return entries;
    }

    public void setEntries(List<ExpenseEntryRequest> entries) {
        this.entries = entries;
    }

    public List<ExpensePurchaseEntryRequest> getPurchaseEntries() {
        return purchaseEntries;
    }

    public void setPurchaseEntries(List<ExpensePurchaseEntryRequest> purchaseEntries) {
        this.purchaseEntries = purchaseEntries;
    }
}
