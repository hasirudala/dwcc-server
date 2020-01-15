package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.ExpenseRecord;
import in.hasirudala.dwcc.server.repository.DwccRepository;
import in.hasirudala.dwcc.server.repository.ExpenseRecordRepository;
import in.hasirudala.dwcc.server.web.contract.ExpenseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ExpenseService {
    private ExpenseRecordRepository recordRepository;
    private DwccRepository dwccRepository;
    private ExpenseEntryService entryService;
    private ExpensePurchaseEntryService purchaseEntryService;

    @Autowired
    public ExpenseService(ExpenseRecordRepository recordRepository,
                          DwccRepository dwccRepository,
                          ExpenseEntryService entryService,
                          ExpensePurchaseEntryService purchaseEntryService) {
        this.recordRepository = recordRepository;
        this.dwccRepository = dwccRepository;
        this.entryService = entryService;
        this.purchaseEntryService = purchaseEntryService;
    }

    public Page<ExpenseRecord> getAllRecords(Pageable pageable) {
        return recordRepository.findAll(pageable);
    }

    public ExpenseRecord createFromRequest(ExpenseRequest request) {
        ExpenseRecord record = new ExpenseRecord();
        record.assignUuid();
        record.setDate(request.getDate());
        record.setDwcc(dwccRepository.getOne(request.getDwccId()));
        entryService.createAndAdd(record, request.getEntries());
        purchaseEntryService.createAndAdd(record, request.getPurchaseEntries());
        recordRepository.save(record);
        return record;
    }

    public ExpenseRecord updateFromRequest(ExpenseRecord record, ExpenseRequest request) {
        entryService.update(record, request.getEntries());
        purchaseEntryService.update(record, request.getPurchaseEntries());
        recordRepository.save(record);
        return record;
    }
}
