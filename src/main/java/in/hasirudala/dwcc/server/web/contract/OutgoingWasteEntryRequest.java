package in.hasirudala.dwcc.server.web.contract;

import java.sql.Date;
import java.util.Set;

public class OutgoingWasteEntryRequest extends BaseRequest {
    private Set<Long> itemIds;
    private Double quantity;
    private Double stockInHand;
    private Double rate;
    private Long buyerId = null;
    private String vehicleNumber;
    private String weighbridgeSlipNumber;
    private String invoiceNumber;
    private Date invoiceDate;

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

    public Double getStockInHand() {
        return stockInHand;
    }

    public void setStockInHand(Double stockInHand) {
        this.stockInHand = stockInHand;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getWeighbridgeSlipNumber() {
        return weighbridgeSlipNumber;
    }

    public void setWeighbridgeSlipNumber(String weighbridgeSlipNumber) {
        this.weighbridgeSlipNumber = weighbridgeSlipNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
