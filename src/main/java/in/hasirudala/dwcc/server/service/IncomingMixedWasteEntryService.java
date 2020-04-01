package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.IncomingMixedWasteEntry;
import in.hasirudala.dwcc.server.domain.IncomingWasteRecord;
import in.hasirudala.dwcc.server.repository.WasteItemRepository;
import in.hasirudala.dwcc.server.repository.WasteSourceRepository;
import in.hasirudala.dwcc.server.web.contract.IncomingMixedWasteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class IncomingMixedWasteEntryService extends AbstractEntryService<IncomingMixedWasteRequest, IncomingMixedWasteEntry, IncomingWasteRecord> {
    private WasteItemRepository wasteItemRepository;
    private WasteSourceRepository wasteSourceRepository;

    @Autowired
    public IncomingMixedWasteEntryService(WasteItemRepository wasteItemRepository,
                                          WasteSourceRepository wasteSourceRepository) {
        this.wasteItemRepository = wasteItemRepository;
        this.wasteSourceRepository = wasteSourceRepository;
    }

    public void createAndAdd(IncomingWasteRecord record, List<IncomingMixedWasteRequest> payloads) {
        for (IncomingMixedWasteRequest payload : payloads) {
            record.addMixedWaste(create(payload));
        }
    }

    public IncomingMixedWasteEntry create(IncomingMixedWasteRequest payload) {
        IncomingMixedWasteEntry wasteItem = new IncomingMixedWasteEntry();
        wasteItem.assignUuid();
        setAttributes(wasteItem, payload);
        return wasteItem;
    }

    public void setAttributes(IncomingMixedWasteEntry mixedWasteItem, IncomingMixedWasteRequest payload) {
        mixedWasteItem.setItems(new HashSet<>(wasteItemRepository.findAllById(payload.getItemIds())));
        mixedWasteItem.setQuantity(payload.getQuantity());
        mixedWasteItem.setRejectQty(payload.getRejectQty());
        mixedWasteItem.setRate(payload.getRate());
        mixedWasteItem.setSourceOfWaste(wasteSourceRepository.getOne(payload.getSourceId()));
        mixedWasteItem.setBillNumber(payload.getBillNumber());
    }

    public void update(IncomingWasteRecord record, List<IncomingMixedWasteRequest> payloads) {
        removeDeletedEntries(record.getMixedWaste(), payloads);
        updateExistingEntries(record.getMixedWaste(), payloads);
        addNewIncomingEntries(record, payloads);
    }
}
