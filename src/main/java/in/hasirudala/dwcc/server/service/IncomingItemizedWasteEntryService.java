package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.IncomingWasteItem;
import in.hasirudala.dwcc.server.domain.IncomingWasteRecord;
import in.hasirudala.dwcc.server.repository.WasteItemRepository;
import in.hasirudala.dwcc.server.web.contract.IncomingWasteItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomingItemizedWasteEntryService extends AbstractEntryService<IncomingWasteItemRequest, IncomingWasteItem, IncomingWasteRecord> {
    private WasteItemRepository wasteItemRepository;

    @Autowired
    public IncomingItemizedWasteEntryService(WasteItemRepository wasteItemRepository) {
        this.wasteItemRepository = wasteItemRepository;
    }

    public void createAndAdd(IncomingWasteRecord record, List<IncomingWasteItemRequest> itemPayloads) {
        for (IncomingWasteItemRequest itemPayload : itemPayloads) {
            record.addWasteItem(create(itemPayload));
        }
    }

    public IncomingWasteItem create(IncomingWasteItemRequest payload) {
        IncomingWasteItem wasteItem = new IncomingWasteItem();
        wasteItem.assignUuid();
        setAttributes(wasteItem, payload);
        return wasteItem;
    }

    public void setAttributes(IncomingWasteItem wasteItem, IncomingWasteItemRequest payload) {
        wasteItem.setItem(wasteItemRepository.getOne(payload.getItemId()));
        wasteItem.setQuantity(payload.getQuantity());
        wasteItem.setRejectQuantity(payload.getRejectQuantity());
    }

    public void update(IncomingWasteRecord record, List<IncomingWasteItemRequest> itemPayloads) {
        removeDeletedEntries(record.getWasteItems(), itemPayloads);
        updateExistingEntries(record.getWasteItems(), itemPayloads);
        addNewIncomingEntries(record, itemPayloads);
    }
}
