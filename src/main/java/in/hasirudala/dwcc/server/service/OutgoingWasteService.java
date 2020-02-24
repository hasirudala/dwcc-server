package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.*;
import in.hasirudala.dwcc.server.repository.*;
import in.hasirudala.dwcc.server.web.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class OutgoingWasteService {
    private OutgoingWasteRecordRepository recordRepository;
    private DwccRepository dwccRepository;
    private OutgoingWasteEntryService outgoingEntryService;

    @Autowired
    public OutgoingWasteService(OutgoingWasteRecordRepository recordRepository,
                                DwccRepository dwccRepository,
                                OutgoingWasteEntryService outgoingEntryService) {
        this.recordRepository = recordRepository;
        this.dwccRepository = dwccRepository;
        this.outgoingEntryService = outgoingEntryService;
    }

    public Page<OutgoingWasteRecord> getAllRecords(Pageable pageable) {
        return recordRepository.findAll(pageable);
    }

    public OutgoingWasteRecord createFromRequest(OutgoingWasteRequest request) {
        OutgoingWasteRecord record = new OutgoingWasteRecord();
        record.assignUuid();
        record.setFromDate(request.getFromDate());
        record.setToDate(request.getToDate());
        record.setTotalQuantity(request.getTotalQuantity());
        record.setRejectQuantity(request.getRejectQuantity());
        record.setSanitaryQuantity(request.getSanitaryQuantity());
        record.setDwcc(dwccRepository.getOne(request.getDwccId()));
        outgoingEntryService.createAndAdd(record, request.getEntries());
        record.setNote(request.getNote());
        recordRepository.save(record);
        return record;
    }

    public OutgoingWasteRecord updateFromRequest(OutgoingWasteRecord record, OutgoingWasteRequest request) {
        record.setFromDate(request.getFromDate());
        record.setToDate(request.getToDate());
        record.setTotalQuantity(request.getTotalQuantity());
        record.setRejectQuantity(request.getRejectQuantity());
        record.setSanitaryQuantity(request.getSanitaryQuantity());
        record.setNote(request.getNote());
        outgoingEntryService.update(record, request.getEntries());
        recordRepository.save(record);
        return record;
    }
}
