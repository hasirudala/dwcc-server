package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.OutgoingWasteEntry;
import in.hasirudala.dwcc.server.domain.OutgoingWasteRecord;
import in.hasirudala.dwcc.server.repository.WasteBuyerRepository;
import in.hasirudala.dwcc.server.repository.WasteItemRepository;
import in.hasirudala.dwcc.server.web.contract.OutgoingWasteEntryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class OutgoingWasteEntryService extends AbstractEntryService<OutgoingWasteEntryRequest, OutgoingWasteEntry, OutgoingWasteRecord> {
    private WasteItemRepository wasteItemRepository;
    private WasteBuyerRepository buyerRepository;

    @Autowired
    public OutgoingWasteEntryService(WasteItemRepository wasteItemRepository,
                                     WasteBuyerRepository buyerRepository) {
        this.wasteItemRepository = wasteItemRepository;
        this.buyerRepository = buyerRepository;
    }

    public void createAndAdd(OutgoingWasteRecord record, List<OutgoingWasteEntryRequest> payloads) {
        for (OutgoingWasteEntryRequest payload : payloads) {
            record.addEntry(create(payload));
        }
    }

    public OutgoingWasteEntry create(OutgoingWasteEntryRequest payload) {
        OutgoingWasteEntry entry = new OutgoingWasteEntry();
        entry.assignUuid();
        setAttributes(entry, payload);
        return entry;
    }

    public void setAttributes(OutgoingWasteEntry entry, OutgoingWasteEntryRequest payload) {
        entry.setItems(new HashSet<>(wasteItemRepository.findAllById(payload.getItemIds())));
        entry.setQuantity(payload.getQuantity());
        entry.setStockInHand(payload.getStockInHand());
        entry.setRate(payload.getRate());
        if (payload.getBuyerId() == null)
            entry.setBuyer(null);
        else
            entry.setBuyer(buyerRepository.getOne(payload.getBuyerId()));
    }

    public void update(OutgoingWasteRecord record, List<OutgoingWasteEntryRequest> payloads) {
        removeDeletedEntries(record.getEntries(), payloads);
        updateExistingEntries(record.getEntries(), payloads);
        addNewIncomingEntries(record, payloads);
    }
}
