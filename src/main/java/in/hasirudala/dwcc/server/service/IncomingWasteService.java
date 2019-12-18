package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.*;
import in.hasirudala.dwcc.server.repository.DwccRepository;
import in.hasirudala.dwcc.server.repository.IncomingWasteRecordRepository;
import in.hasirudala.dwcc.server.repository.VehicleTypeRepository;
import in.hasirudala.dwcc.server.repository.WasteItemRepository;
import in.hasirudala.dwcc.server.web.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IncomingWasteService {
    private IncomingWasteRecordRepository recordRepository;
    private DwccRepository dwccRepository;
    private VehicleTypeRepository vehicleTypeRepository;
    private WasteItemRepository wasteItemRepository;

    @Autowired
    public IncomingWasteService(IncomingWasteRecordRepository recordRepository,
                                DwccRepository dwccRepository,
                                VehicleTypeRepository vehicleTypeRepository,
                                WasteItemRepository wasteItemRepository) {
        this.recordRepository = recordRepository;
        this.dwccRepository = dwccRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.wasteItemRepository = wasteItemRepository;
    }

    public Page<IncomingWasteRecord> getAllRecords(Pageable pageable) {
        return recordRepository.findAll(pageable);
    }

    public IncomingWasteRecord createFromRequest(IncomingWasteRequest request) {
        IncomingWasteRecord record = new IncomingWasteRecord();
        record.assignUuid();
        record.setDate(request.getDate());
        record.setDwcc(dwccRepository.getOne(request.getDwccId()));
        createAndAddDtdWaste(record, request.getDtdCollection());
        createAndAddWasteItems(record, request.getWasteItems());
        createAndAddMixedWaste(record, request.getMixedWaste());
        record.setErrorsIgnored(request.isErrorsIgnored());
        record.setApprovedByAdmin(request.isApprovedByAdmin());
        record.setNote(request.getNote());
        recordRepository.save(record);
        return record;
    }

    public IncomingWasteRecord updateFromRequest(IncomingWasteRecord record, IncomingWasteRequest request) {
        record.setErrorsIgnored(request.isErrorsIgnored());
        record.setApprovedByAdmin(request.isApprovedByAdmin());
        record.setNote(request.getNote());
        updateDtdWaste(record, request.getDtdCollection());
        updateWasteItems(record, request.getWasteItems());
        updateMixedWaste(record, request.getMixedWaste());
        recordRepository.save(record);
        return record;
    }

    private void updateDtdWaste(IncomingWasteRecord record, List<IncomingDtdWasteRequest> incomingDtdCollection) {
        removeDeletedEntries(record.getDtdCollection(), incomingDtdCollection);
        updateExistingEntries(record.getDtdCollection(), incomingDtdCollection);
        addNewIncomingEntries(record, incomingDtdCollection);
    }

    private void updateWasteItems(IncomingWasteRecord record, List<IncomingWasteItemRequest> incomingWasteItems) {
        removeDeletedEntries(record.getWasteItems(), incomingWasteItems);
        updateExistingEntries(record.getWasteItems(), incomingWasteItems);
        addNewIncomingEntries(record, incomingWasteItems);
    }

    private void updateMixedWaste(IncomingWasteRecord record, List<IncomingMixedWasteRequest> incomingMixedWaste) {
        removeDeletedEntries(record.getMixedWaste(), incomingMixedWaste);
        updateExistingEntries(record.getMixedWaste(), incomingMixedWaste);
        addNewIncomingEntries(record, incomingMixedWaste);
    }

    private void createAndAddDtdWaste(IncomingWasteRecord record, List<IncomingDtdWasteRequest> dtdCollection) {
        for (IncomingDtdWasteRequest dtdPayload : dtdCollection) {
            record.addDtdWaste(createDtdEntry(dtdPayload));
        }
    }

    private IncomingDtdWaste createDtdEntry(IncomingDtdWasteRequest dtdPayload) {
        IncomingDtdWaste dtdEntry = new IncomingDtdWaste();
        dtdEntry.assignUuid();
        setDtdEntryAttributes(dtdEntry, dtdPayload);
        return dtdEntry;
    }

    private void setDtdEntryAttributes(IncomingDtdWaste dtdEntry, IncomingDtdWasteRequest dtdPayload) {
        dtdEntry.setVehicleNumber(dtdPayload.getVehicleNumber());
        VehicleType vt = vehicleTypeRepository.getOne(dtdPayload.getVehicleTypeId());
        dtdEntry.setVehicleType(vt);
        dtdEntry.setQuantity(dtdPayload.getQuantity());
    }

    private void createAndAddWasteItems(IncomingWasteRecord record, List<IncomingWasteItemRequest> wasteItems) {
        for (IncomingWasteItemRequest itemPayload : wasteItems) {
            record.addWasteItem(createWasteItemEntry(itemPayload));
        }
    }

    private IncomingWasteItem createWasteItemEntry(IncomingWasteItemRequest itemPayload) {
        IncomingWasteItem wasteItem = new IncomingWasteItem();
        wasteItem.assignUuid();
        setWasteItemEntryAttributes(wasteItem, itemPayload);
        return wasteItem;
    }

    private void setWasteItemEntryAttributes(IncomingWasteItem wasteItem, IncomingWasteItemRequest itemPayload) {
        wasteItem.setItem(wasteItemRepository.getOne(itemPayload.getItemId()));
        wasteItem.setQuantity(itemPayload.getQuantity());
        wasteItem.setRejectQuantity(itemPayload.getRejectQuantity());
    }

    private void createAndAddMixedWaste(IncomingWasteRecord record, List<IncomingMixedWasteRequest> mixedWaste) {
        for (IncomingMixedWasteRequest mixedWasteItemPayload : mixedWaste) {
            record.addMixedWaste(createMixedWasteEntry(mixedWasteItemPayload));
        }
    }

    private IncomingMixedWaste createMixedWasteEntry(IncomingMixedWasteRequest mixedWasteItemPayload) {
        IncomingMixedWaste mixedWasteItem = new IncomingMixedWaste();
        mixedWasteItem.assignUuid();
        setMixedWasteEntryAttributes(mixedWasteItem, mixedWasteItemPayload);
        return mixedWasteItem;
    }

    private void setMixedWasteEntryAttributes(IncomingMixedWaste mixedWasteItem,
                                              IncomingMixedWasteRequest mixedWasteItemPayload) {
        mixedWasteItem.setItems(new HashSet<>(wasteItemRepository.findAllById(mixedWasteItemPayload.getItemIds())));
        mixedWasteItem.setQuantity(mixedWasteItemPayload.getQuantity());
        mixedWasteItem.setRate(mixedWasteItemPayload.getRate());
    }

    private void removeDeletedEntries(Set<? extends BaseEntity> existingEntries,
                                      List<? extends BaseRequest> incomingItems) {
        List<Long> idsOfIncomingItems = incomingItems
                                                .stream()
                                                .map(BaseRequest::getId)
                                                .collect(Collectors.toList());
        existingEntries
                .removeIf(entry -> !idsOfIncomingItems.contains(entry.getId()));
    }

    private void updateExistingEntries(Set<? extends BaseEntity> existingEntries,
                                       List<? extends BaseRequest> incomingItems) {
        incomingItems
                .stream()
                .filter(item -> item.getId() != null)
                .forEach(updatedItem ->
                                 existingEntries
                                         .stream()
                                         .filter(entry -> entry.getId().equals(updatedItem.getId()))
                                         .findFirst()
                                         .ifPresent(existingEntry -> {
                                             if (existingEntry instanceof IncomingDtdWaste)
                                                 setDtdEntryAttributes(
                                                         (IncomingDtdWaste) existingEntry,
                                                         (IncomingDtdWasteRequest) updatedItem);
                                             else if (existingEntry instanceof IncomingWasteItem)
                                                 setWasteItemEntryAttributes(
                                                         (IncomingWasteItem) existingEntry,
                                                         (IncomingWasteItemRequest) updatedItem);
                                             else if (existingEntry instanceof IncomingMixedWaste)
                                                 setMixedWasteEntryAttributes(
                                                         (IncomingMixedWaste) existingEntry,
                                                         (IncomingMixedWasteRequest) updatedItem);
                                         })
                );
    }

    private void addNewIncomingEntries(IncomingWasteRecord record, List<? extends BaseRequest> incomingItems) {
        incomingItems
                .stream()
                .filter(item -> item.getId() == null)
                .forEach(payload -> {
                    if (payload instanceof IncomingDtdWasteRequest)
                        record.addDtdWaste(createDtdEntry((IncomingDtdWasteRequest) payload));
                    else if (payload instanceof IncomingWasteItemRequest)
                        record.addWasteItem(createWasteItemEntry((IncomingWasteItemRequest) payload));
                    else if (payload instanceof IncomingMixedWasteRequest)
                        record.addMixedWaste(createMixedWasteEntry((IncomingMixedWasteRequest) payload));
                });
    }
}
