package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.*;
import in.hasirudala.dwcc.server.repository.DwccRepository;
import in.hasirudala.dwcc.server.repository.IncomingWasteRecordRepository;
import in.hasirudala.dwcc.server.web.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class IncomingWasteService {
    private IncomingWasteRecordRepository recordRepository;
    private DwccRepository dwccRepository;
    private IncomingDtdWasteEntryService dtdEntryService;
    private IncomingMixedWasteEntryService mixedWasteEntryService;

    @Autowired
    public IncomingWasteService(IncomingWasteRecordRepository recordRepository,
                                DwccRepository dwccRepository,
                                IncomingDtdWasteEntryService dtdEntryService,
                                //IncomingItemizedWasteEntryService itemsEntryService,
                                IncomingMixedWasteEntryService mixedWasteEntryService) {
        this.recordRepository = recordRepository;
        this.dwccRepository = dwccRepository;
        this.dtdEntryService = dtdEntryService;
        this.mixedWasteEntryService = mixedWasteEntryService;
    }

    public Page<IncomingWasteRecord> getAllRecords(Pageable pageable) {
        return recordRepository.findAll(pageable);
    }

    public IncomingWasteRecord createFromRequest(IncomingWasteRecordRequest request) {
        IncomingWasteRecord record = new IncomingWasteRecord();
        record.assignUuid();
        record.setDate(request.getDate());
        record.setDwcc(dwccRepository.getOne(request.getDwccId()));
        dtdEntryService.createAndAdd(record, request.getDtdCollection());
        mixedWasteEntryService.createAndAdd(record, request.getMixedWaste());
        record.setNote(request.getNote());
        recordRepository.save(record);
        return record;
    }

    public IncomingWasteRecord updateFromRequest(IncomingWasteRecord record, IncomingWasteRecordRequest request) {
        record.setNote(request.getNote());
        dtdEntryService.update(record, request.getDtdCollection());
        mixedWasteEntryService.update(record, request.getMixedWaste());
        recordRepository.save(record);
        return record;
    }
}
