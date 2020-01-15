package in.hasirudala.dwcc.server.web.contract;

public class ExpensePurchaseEntryRequest extends BaseRequest {
    private Long wasteItemId;
    private Double amount;

    public Long getWasteItemId() {
        return wasteItemId;
    }

    public void setWasteItemId(Long wasteItemId) {
        this.wasteItemId = wasteItemId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
