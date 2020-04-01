package in.hasirudala.dwcc.server.web.contract;

import java.util.Set;

public class IncomingMixedWasteRequest extends BaseRequest {
    private Set<Long> itemIds;
    private Double quantity;
    private Double rejectQty;
    private Double rate;
    private Long sourceId = null;
    private String billNumber;

    public Set<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(Set<Long> itemIds) {
        this.itemIds = itemIds;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getRejectQty() {
        return rejectQty;
    }

    public void setRejectQty(Double rejectQty) {
        this.rejectQty = rejectQty;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

}
