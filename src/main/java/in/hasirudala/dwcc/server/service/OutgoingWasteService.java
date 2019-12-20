package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.*;
import in.hasirudala.dwcc.server.repository.*;
import in.hasirudala.dwcc.server.web.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OutgoingWasteService {
    private OutgoingWasteRecordRepository recordRepository;
    private DwccRepository dwccRepository;
    private WasteItemRepository wasteItemRepository;
    private WasteBuyerRepository buyerRepository;

    @Autowired
    public OutgoingWasteService(OutgoingWasteRecordRepository recordRepository,
                                DwccRepository dwccRepository,
                                WasteItemRepository wasteItemRepository,
                                WasteBuyerRepository buyerRepository) {
        this.recordRepository = recordRepository;
        this.dwccRepository = dwccRepository;
        this.wasteItemRepository = wasteItemRepository;
        this.buyerRepository = buyerRepository;
    }

    public Page<OutgoingWasteRecord> getAllRecords(Pageable pageable) {
        return recordRepository.findAll(pageable);
    }

    public OutgoingWasteRecord createFromRequest(OutgoingWasteRequest request) {
        OutgoingWasteRecord record = new OutgoingWasteRecord();
        record.assignUuid();
        record.setDate(request.getDate());
        record.setDwcc(dwccRepository.getOne(request.getDwccId()));
        createAndAddWasteItems(record, request.getWasteItems());
        record.setNote(request.getNote());
        recordRepository.save(record);
        return record;
    }

    public OutgoingWasteRecord updateFromRequest(OutgoingWasteRecord record, OutgoingWasteRequest request) {
        record.setNote(request.getNote());
        updateWasteItems(record, request.getWasteItems());
        recordRepository.save(record);
        return record;
    }

    private void updateWasteItems(OutgoingWasteRecord record, List<OutgoingWasteItemRequest> outgoingWasteItems) {
        removeDeletedEntries(record.getWasteItems(), outgoingWasteItems);
        updateExistingEntries(record.getWasteItems(), outgoingWasteItems);
        addNewOutgoingEntries(record, outgoingWasteItems);
    }

    private void createAndAddWasteItems(OutgoingWasteRecord record, List<OutgoingWasteItemRequest> wasteItems) {
        for (OutgoingWasteItemRequest itemPayload : wasteItems) {
            record.addWasteItem(createWasteItemEntry(itemPayload));
        }
    }

    private OutgoingWasteItem createWasteItemEntry(OutgoingWasteItemRequest itemPayload) {
        OutgoingWasteItem wasteItem = new OutgoingWasteItem();
        wasteItem.assignUuid();
        setWasteItemEntryAttributes(wasteItem, itemPayload);
        return wasteItem;
    }

    private void setWasteItemEntryAttributes(OutgoingWasteItem wasteItem, OutgoingWasteItemRequest itemPayload) {
        wasteItem.setItem(wasteItemRepository.getOne(itemPayload.getItemId()));
        wasteItem.setQuantity(itemPayload.getQuantity());
        wasteItem.setRate(itemPayload.getRate());
        if (itemPayload.getBuyerId() == null)
            wasteItem.setBuyer(null);
        else
            wasteItem.setBuyer(buyerRepository.getOne(itemPayload.getBuyerId()));
    }

    private void removeDeletedEntries(Set<OutgoingWasteItem> existingEntries,
                                      List<OutgoingWasteItemRequest> outgoingItems) {
        List<Long> idsOfOutgoingItems = outgoingItems
                                                .stream()
                                                .map(BaseRequest::getId)
                                                .collect(Collectors.toList());
        existingEntries
                .removeIf(entry -> !idsOfOutgoingItems.contains(entry.getId()));
    }

    private void updateExistingEntries(Set<OutgoingWasteItem> existingEntries,
                                       List<OutgoingWasteItemRequest> outgoingItems) {
        outgoingItems
                .stream()
                .filter(item -> item.getId() != null)
                .forEach(updatedItem ->
                                 existingEntries
                                         .stream()
                                         .filter(entry -> entry.getId().equals(updatedItem.getId()))
                                         .findFirst()
                                         .ifPresent(existingEntry ->
                                                            setWasteItemEntryAttributes(existingEntry, updatedItem)));
    }

    private void addNewOutgoingEntries(OutgoingWasteRecord record, List<OutgoingWasteItemRequest> outgoingItems) {
        outgoingItems
                .stream()
                .filter(item -> item.getId() == null)
                .forEach(payload ->
                                 record.addWasteItem(createWasteItemEntry(payload)));
    }
}
