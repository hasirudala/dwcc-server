package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Entity
@Table(name = "incoming_waste_dtd")
public class IncomingDtdWaste extends BaseEntity {
    @Column(nullable = false)
    private String vehicleNumber;

    @JsonIgnoreProperties({"id", "uuid", "hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    private VehicleType vehicleType;


    @Column(nullable = false)
    private Double quantity;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private IncomingWasteRecord record;

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Long getVehicleTypeId() {
        return this.vehicleType.getId();
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public IncomingWasteRecord getRecord() {
        return record;
    }

    public void setRecord(IncomingWasteRecord wasteRecord) {
        this.record = wasteRecord;
    }

    public Long getRecordId() {
        return this.record.getId();
    }
}
