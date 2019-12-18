package in.hasirudala.dwcc.server.web.contract;

public class IncomingWasteItemRequest extends BaseRequest {
    private Long itemId;
    private Double quantity;
    private Double rejectQuantity;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getRejectQuantity() {
        return rejectQuantity;
    }

    public void setRejectQuantity(Double rejectQuantity) {
        this.rejectQuantity = rejectQuantity;
    }
}
