package in.hasirudala.dwcc.server.web.contract;

import java.util.Set;

public class IncomingMixedWasteRequest extends BaseRequest {
    private Set<Long> itemIds;
    private Double quantity;
    private Double rate;

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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
