package in.hasirudala.dwcc.server.web.contract;

import java.util.Set;

public class ExpensePurchaseEntryRequest extends BaseRequest {
    private Set<Long> wasteItemIds;
    private Double amount;

    public Set<Long> getWasteItemIds() {
        return wasteItemIds;
    }

    public void setWasteItemIds(Set<Long> wasteItemIds) {
        this.wasteItemIds = wasteItemIds;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
