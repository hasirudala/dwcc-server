package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.ExpenseRecord;
import in.hasirudala.dwcc.server.repository.ExpenseRecordRepository;
import in.hasirudala.dwcc.server.service.ExpenseService;
import in.hasirudala.dwcc.server.web.contract.ExpenseRequest;
import in.hasirudala.dwcc.server.web.messages.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseRecordController {

    private ExpenseService expenseService;
    private ExpenseRecordRepository recordRepository;

    @Autowired
    public ExpenseRecordController(ExpenseService expenseService,
                                   ExpenseRecordRepository recordRepository) {
        this.expenseService = expenseService;
        this.recordRepository = recordRepository;
    }

    @GetMapping
    public Page<ExpenseRecord> listIncomingRecords(Pageable pageable) {
        return expenseService.getAllRecords(pageable);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ExpenseRecord> create(@RequestBody ExpenseRequest payload) {
        ExpenseRecord record = expenseService.createFromRequest(payload);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseRecord> getRecord(@PathVariable("id") Long id) {
        ExpenseRecord record;
        try {
            record = recordRepository
                             .findById(id)
                             .orElseThrow(() -> new NoSuchElementException(
                                     ErrorMessages.getRecordNotFoundMsg("ExpenseRecord", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecord(@PathVariable("id") Long id,
                                          @RequestBody ExpenseRequest request) {
        ExpenseRecord existingRecord;
        try {
            existingRecord = recordRepository
                                     .findById(id)
                                     .orElseThrow(() -> new NoSuchElementException(
                                             ErrorMessages.getRecordNotFoundMsg("ExpenseRecord", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingRecord = expenseService.updateFromRequest(existingRecord, request);
        return new ResponseEntity<>(existingRecord, HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ExpenseRecord> delete(@PathVariable("id") Long id) {
        ExpenseRecord recordToDelete;
        try {
            recordToDelete = recordRepository
                                     .findById(id)
                                     .orElseThrow(() -> new NoSuchElementException(
                                             ErrorMessages.getRecordNotFoundMsg("ExpenseRecord", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        recordRepository.delete(recordToDelete);
        return new ResponseEntity<>(recordToDelete, HttpStatus.OK);
    }
}
