package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.IncomingMixedWaste;
import in.hasirudala.dwcc.server.domain.IncomingWasteRecord;
import in.hasirudala.dwcc.server.repository.WasteItemRepository;
import in.hasirudala.dwcc.server.web.contract.IncomingMixedWasteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class IncomingMixedWasteEntryService extends AbstractWasteEntryService<IncomingMixedWasteRequest, IncomingMixedWaste, IncomingWasteRecord> {
    private WasteItemRepository wasteItemRepository;

    @Autowired
    public IncomingMixedWasteEntryService(WasteItemRepository wasteItemRepository) {
        this.wasteItemRepository = wasteItemRepository;
    }

    public void createAndAdd(IncomingWasteRecord record, List<IncomingMixedWasteRequest> payloads) {
        for (IncomingMixedWasteRequest payload : payloads) {
            record.addMixedWaste(create(payload));
        }
    }

    public IncomingMixedWaste create(IncomingMixedWasteRequest payload) {
        IncomingMixedWaste wasteItem = new IncomingMixedWaste();
        wasteItem.assignUuid();
        setAttributes(wasteItem, payload);
        return wasteItem;
    }

    public void setAttributes(IncomingMixedWaste mixedWasteItem, IncomingMixedWasteRequest payload) {
        mixedWasteItem.setItems(new HashSet<>(wasteItemRepository.findAllById(payload.getItemIds())));
        mixedWasteItem.setQuantity(payload.getQuantity());
        mixedWasteItem.setRate(payload.getRate());
    }

    public void update(IncomingWasteRecord record, List<IncomingMixedWasteRequest> payloads) {
        removeDeletedEntries(record.getMixedWaste(), payloads);
        updateExistingEntries(record.getMixedWaste(), payloads);
        addNewIncomingEntries(record, payloads);
    }
}
