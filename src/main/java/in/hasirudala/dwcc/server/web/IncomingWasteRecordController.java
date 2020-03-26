package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.*;
import in.hasirudala.dwcc.server.repository.IncomingWasteRecordRepository;
import in.hasirudala.dwcc.server.service.IncomingWasteService;
import in.hasirudala.dwcc.server.web.contract.IncomingWasteRecordRequest;
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
@RequestMapping("/api/incomingWaste")
public class IncomingWasteRecordController {

    private IncomingWasteService incomingWasteService;
    private IncomingWasteRecordRepository recordRepository;

    @Autowired
    public IncomingWasteRecordController(IncomingWasteService incomingWasteService,
                                         IncomingWasteRecordRepository recordRepository) {
        this.incomingWasteService = incomingWasteService;
        this.recordRepository = recordRepository;
    }

    @GetMapping
    public Page<IncomingWasteRecord> listIncomingRecords(Pageable pageable) {
        return incomingWasteService.getAllRecords(pageable);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<IncomingWasteRecord> create(@RequestBody IncomingWasteRecordRequest payload) {
        IncomingWasteRecord record = incomingWasteService.createFromRequest(payload);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomingWasteRecord> getRecord(@PathVariable("id") Long id) {
        IncomingWasteRecord record;
        try {
            record = recordRepository
                             .findById(id)
                             .orElseThrow(() -> new NoSuchElementException(
                                     ErrorMessages.getRecordNotFoundMsg("IncomingWasteRecord", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecord(@PathVariable("id") Long id,
                                          @RequestBody IncomingWasteRecordRequest request) {
        IncomingWasteRecord existingRecord;
        try {
            existingRecord = recordRepository
                                     .findById(id)
                                     .orElseThrow(() -> new NoSuchElementException(
                                             ErrorMessages.getRecordNotFoundMsg("IncomingWasteRecord", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingRecord = incomingWasteService.updateFromRequest(existingRecord, request);
        return new ResponseEntity<>(existingRecord, HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<IncomingWasteRecord> delete(@PathVariable("id") Long id) {
        IncomingWasteRecord recordToDelete;
        try {
            recordToDelete = recordRepository
                                     .findById(id)
                                     .orElseThrow(() -> new NoSuchElementException(
                                             ErrorMessages.getRecordNotFoundMsg("IncomingWasteRecord", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        recordRepository.delete(recordToDelete);
        return new ResponseEntity<>(recordToDelete, HttpStatus.OK);
    }
}
