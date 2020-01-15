package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.ExpenseEntry;
import in.hasirudala.dwcc.server.domain.ExpenseItem;
import in.hasirudala.dwcc.server.domain.ExpenseRecord;
import in.hasirudala.dwcc.server.repository.ExpenseItemRepository;
import in.hasirudala.dwcc.server.web.contract.ExpenseEntryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseEntryService extends AbstractEntryService<ExpenseEntryRequest, ExpenseEntry, ExpenseRecord> {
    private ExpenseItemRepository expenseItemRepository;

    @Autowired
    public ExpenseEntryService(ExpenseItemRepository expenseItemRepository) {
        this.expenseItemRepository = expenseItemRepository;
    }

    public void createAndAdd(ExpenseRecord record, List<ExpenseEntryRequest> itemPayloads) {
        for (ExpenseEntryRequest itemPayload : itemPayloads) {
            record.addEntry(create(itemPayload));
        }
    }

    public ExpenseEntry create(ExpenseEntryRequest payload) {
        ExpenseEntry entry = new ExpenseEntry();
        entry.assignUuid();
        setAttributes(entry, payload);
        return entry;
    }

    public void setAttributes(ExpenseEntry entry, ExpenseEntryRequest payload) {
        ExpenseItem item = expenseItemRepository.getOne(payload.getExpenseItemId());
        entry.setItem(item);
        if (item.askNumberOfUnits())
            entry.setNumberOfItems(payload.getNumItems());
        entry.setAmount(payload.getAmount());

    }

    public void update(ExpenseRecord record, List<ExpenseEntryRequest> itemPayloads) {
        removeDeletedEntries(record.getEntries(), itemPayloads);
        updateExistingEntries(record.getEntries(), itemPayloads);
        addNewIncomingEntries(record, itemPayloads);
    }
}
