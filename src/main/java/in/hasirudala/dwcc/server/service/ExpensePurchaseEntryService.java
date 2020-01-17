package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.ExpensePurchaseEntry;
import in.hasirudala.dwcc.server.domain.ExpenseRecord;
import in.hasirudala.dwcc.server.repository.WasteItemRepository;
import in.hasirudala.dwcc.server.web.contract.ExpensePurchaseEntryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ExpensePurchaseEntryService extends AbstractEntryService<ExpensePurchaseEntryRequest, ExpensePurchaseEntry, ExpenseRecord> {
    private WasteItemRepository wasteItemRepository;

    @Autowired
    public ExpensePurchaseEntryService(WasteItemRepository wasteItemRepository) {
        this.wasteItemRepository = wasteItemRepository;
    }

    public void createAndAdd(ExpenseRecord record, List<ExpensePurchaseEntryRequest> itemPayloads) {
        for (ExpensePurchaseEntryRequest itemPayload : itemPayloads) {
            record.addPurchaseEntry(create(itemPayload));
        }
    }

    public ExpensePurchaseEntry create(ExpensePurchaseEntryRequest payload) {
        ExpensePurchaseEntry entry = new ExpensePurchaseEntry();
        entry.assignUuid();
        setAttributes(entry, payload);
        return entry;
    }

    public void setAttributes(ExpensePurchaseEntry entry, ExpensePurchaseEntryRequest payload) {
        entry.setItems(new HashSet<>(wasteItemRepository.findAllById(payload.getWasteItemIds())));
        entry.setAmount(payload.getAmount());
    }

    public void update(ExpenseRecord record, List<ExpensePurchaseEntryRequest> itemPayloads) {
        removeDeletedEntries(record.getPurchaseEntries(), itemPayloads);
        updateExistingEntries(record.getPurchaseEntries(), itemPayloads);
        addNewIncomingEntries(record, itemPayloads);
    }
}
