package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.OutgoingWasteRecord;
import in.hasirudala.dwcc.server.repository.OutgoingWasteRecordRepository;
import in.hasirudala.dwcc.server.service.OutgoingWasteService;
import in.hasirudala.dwcc.server.web.contract.OutgoingWasteRequest;
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
@RequestMapping("/api/outgoingWaste")
public class OutgoingWasteRecordController {

    private OutgoingWasteService outgoingWasteService;
    private OutgoingWasteRecordRepository recordRepository;

    @Autowired
    public OutgoingWasteRecordController(OutgoingWasteService outgoingWasteService,
                                         OutgoingWasteRecordRepository recordRepository) {
        this.outgoingWasteService = outgoingWasteService;
        this.recordRepository = recordRepository;
    }

    @GetMapping
    public Page<OutgoingWasteRecord> listOutgoingRecords(Pageable pageable) {
        return outgoingWasteService.getAllRecords(pageable);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<OutgoingWasteRecord> create(@RequestBody OutgoingWasteRequest payload) {
        OutgoingWasteRecord record = outgoingWasteService.createFromRequest(payload);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutgoingWasteRecord> getRecord(@PathVariable("id") Long id) {
        OutgoingWasteRecord record;
        try {
            record = recordRepository
                             .findById(id)
                             .orElseThrow(() -> new NoSuchElementException(
                                     ErrorMessages.getRecordNotFoundMsg("OutgoingWasteRecord", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecord(@PathVariable("id") Long id,
                                          @RequestBody OutgoingWasteRequest request) {
        OutgoingWasteRecord existingRecord;
        try {
            existingRecord = recordRepository
                                     .findById(id)
                                     .orElseThrow(() -> new NoSuchElementException(
                                             ErrorMessages.getRecordNotFoundMsg("OutgoingWasteRecord", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingRecord = outgoingWasteService.updateFromRequest(existingRecord, request);
        return new ResponseEntity<>(existingRecord, HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<OutgoingWasteRecord> delete(@PathVariable("id") Long id) {
        OutgoingWasteRecord recordToDelete;
        try {
            recordToDelete = recordRepository
                                     .findById(id)
                                     .orElseThrow(() -> new NoSuchElementException(
                                             ErrorMessages.getRecordNotFoundMsg("OutgoingWasteRecord", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        recordRepository.delete(recordToDelete);
        return new ResponseEntity<>(recordToDelete, HttpStatus.OK);
    }
}
