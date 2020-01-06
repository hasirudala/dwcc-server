package in.hasirudala.dwcc.server.web.contract;

public class IncomingDtdWasteRequest extends BaseRequest {
    private String vehicleNumber;
    private Long vehicleTypeId;
    private Double quantity;
    private Double rejectQty;
    private Double sanitaryQty;

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Long getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Long vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
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

    public Double getSanitaryQty() {
        return sanitaryQty;
    }

    public void setSanitaryQty(Double sanitaryQty) {
        this.sanitaryQty = sanitaryQty;
    }
}
