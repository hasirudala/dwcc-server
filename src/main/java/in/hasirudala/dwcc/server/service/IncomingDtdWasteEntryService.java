package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.IncomingDtdWasteEntry;
import in.hasirudala.dwcc.server.domain.IncomingWasteRecord;
import in.hasirudala.dwcc.server.domain.VehicleType;
import in.hasirudala.dwcc.server.repository.VehicleTypeRepository;
import in.hasirudala.dwcc.server.web.contract.IncomingDtdWasteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomingDtdWasteEntryService extends AbstractEntryService<IncomingDtdWasteRequest, IncomingDtdWasteEntry, IncomingWasteRecord> {
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

    public IncomingDtdWasteEntry create(IncomingDtdWasteRequest payload) {
        IncomingDtdWasteEntry dtdEntry = new IncomingDtdWasteEntry();
        dtdEntry.assignUuid();
        setAttributes(dtdEntry, payload);
        return dtdEntry;
    }

    public void setAttributes(IncomingDtdWasteEntry dtdEntry, IncomingDtdWasteRequest payload) {
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
