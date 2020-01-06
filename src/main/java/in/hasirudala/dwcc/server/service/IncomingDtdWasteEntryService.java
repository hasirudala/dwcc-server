package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.IncomingDtdWaste;
import in.hasirudala.dwcc.server.domain.IncomingWasteRecord;
import in.hasirudala.dwcc.server.domain.VehicleType;
import in.hasirudala.dwcc.server.repository.VehicleTypeRepository;
import in.hasirudala.dwcc.server.web.contract.IncomingDtdWasteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomingDtdWasteEntryService extends AbstractWasteEntryService<IncomingDtdWasteRequest, IncomingDtdWaste, IncomingWasteRecord> {
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    public IncomingDtdWasteEntryService(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public void createAndAdd(IncomingWasteRecord record, List<IncomingDtdWasteRequest> dtdPayloads) {
        for (IncomingDtdWasteRequest dtdPayload : dtdPayloads) {
            record.addDtdWaste(create(dtdPayload));
        }
    }

    public IncomingDtdWaste create(IncomingDtdWasteRequest payload) {
        IncomingDtdWaste dtdEntry = new IncomingDtdWaste();
        dtdEntry.assignUuid();
        setAttributes(dtdEntry, payload);
        return dtdEntry;
    }

    public void setAttributes(IncomingDtdWaste dtdEntry, IncomingDtdWasteRequest payload) {
        dtdEntry.setVehicleNumber(payload.getVehicleNumber());
        VehicleType vt = vehicleTypeRepository.getOne(payload.getVehicleTypeId());
        dtdEntry.setVehicleType(vt);
        dtdEntry.setQuantity(payload.getQuantity());
        dtdEntry.setRejectQty(payload.getRejectQty());
        dtdEntry.setSanitaryQty(payload.getSanitaryQty());
    }

    public void update(IncomingWasteRecord record, List<IncomingDtdWasteRequest> dtdPayloads) {
        removeDeletedEntries(record.getDtdCollection(), dtdPayloads);
        updateExistingEntries(record.getDtdCollection(), dtdPayloads);
        addNewIncomingEntries(record, dtdPayloads);
    }
}
