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
    //private IncomingItemizedWasteEntryService itemsEntryService;
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
        //this.itemsEntryService = itemsEntryService;
        this.mixedWasteEntryService = mixedWasteEntryService;
    }

    public Page<IncomingWasteRecord> getAllRecords(Pageable pageable) {
        return recordRepository.findAll(pageable);
    }

    public IncomingWasteRecord createFromRequest(IncomingWasteRequest request) {
        IncomingWasteRecord record = new IncomingWasteRecord();
        record.assignUuid();
        record.setDate(request.getDate());
        record.setDwcc(dwccRepository.getOne(request.getDwccId()));
        dtdEntryService.createAndAdd(record, request.getDtdCollection());
        //itemsEntryService.createAndAdd(record, request.getWasteItems());
        mixedWasteEntryService.createAndAdd(record, request.getMixedWaste());
        //record.setErrorsIgnored(request.isErrorsIgnored());
        //record.setApprovedByAdmin(request.isApprovedByAdmin());
        record.setNote(request.getNote());
        recordRepository.save(record);
        return record;
    }

    public IncomingWasteRecord updateFromRequest(IncomingWasteRecord record, IncomingWasteRequest request) {
        //record.setErrorsIgnored(request.isErrorsIgnored());
        //record.setApprovedByAdmin(request.isApprovedByAdmin());
        record.setNote(request.getNote());
        dtdEntryService.update(record, request.getDtdCollection());
        //itemsEntryService.update(record, request.getWasteItems());
        mixedWasteEntryService.update(record, request.getMixedWaste());
        recordRepository.save(record);
        return record;
    }
}
