package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.OutgoingWasteItem;
import in.hasirudala.dwcc.server.domain.OutgoingWasteRecord;
import in.hasirudala.dwcc.server.repository.WasteBuyerRepository;
import in.hasirudala.dwcc.server.repository.WasteItemRepository;
import in.hasirudala.dwcc.server.web.contract.OutgoingWasteItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutgoingWasteEntryService extends AbstractWasteEntryService<OutgoingWasteItemRequest, OutgoingWasteItem, OutgoingWasteRecord> {
    private WasteItemRepository wasteItemRepository;
    private WasteBuyerRepository buyerRepository;

    @Autowired
    public OutgoingWasteEntryService(WasteItemRepository wasteItemRepository, WasteBuyerRepository buyerRepository) {
        this.wasteItemRepository = wasteItemRepository;
        this.buyerRepository = buyerRepository;
    }

    public void createAndAdd(OutgoingWasteRecord record, List<OutgoingWasteItemRequest> payloads) {
        for (OutgoingWasteItemRequest payload : payloads) {
            record.addWasteItem(create(payload));
        }
    }

    public OutgoingWasteItem create(OutgoingWasteItemRequest payload) {
        OutgoingWasteItem wasteItem = new OutgoingWasteItem();
        wasteItem.assignUuid();
        setAttributes(wasteItem, payload);
        return wasteItem;
    }

    public void setAttributes(OutgoingWasteItem wasteItem, OutgoingWasteItemRequest payload) {
        wasteItem.setItem(wasteItemRepository.getOne(payload.getItemId()));
        wasteItem.setQuantity(payload.getQuantity());
        wasteItem.setRate(payload.getRate());
        if (payload.getBuyerId() == null)
            wasteItem.setBuyer(null);
        else
            wasteItem.setBuyer(buyerRepository.getOne(payload.getBuyerId()));
    }

    public void update(OutgoingWasteRecord record, List<OutgoingWasteItemRequest> payloads) {
        removeDeletedEntries(record.getWasteItems(), payloads);
        updateExistingEntries(record.getWasteItems(), payloads);
        addNewIncomingEntries(record, payloads);
    }
}
