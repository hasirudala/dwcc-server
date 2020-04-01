package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.WasteSource;
import in.hasirudala.dwcc.server.repository.WasteSourceRepository;
import in.hasirudala.dwcc.server.web.messages.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/wasteSources")
public class WasteSourceController {
    private WasteSourceRepository wasteSourceRepository;

    public WasteSourceController(WasteSourceRepository wasteSourceRepository) {
        this.wasteSourceRepository = wasteSourceRepository;
    }

    @GetMapping
    public Page<WasteSource> listWasteSources(Pageable pageable) {
        return wasteSourceRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteSource> getWasteSource(@PathVariable("id") Long id) {
        WasteSource wasteSource;
        try {
            wasteSource = wasteSourceRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("WasteSource", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(wasteSource, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<WasteSource> create(@RequestBody WasteSource wasteSource) {
        wasteSource.assignUuid();
        WasteSource newWasteSource = wasteSourceRepository.save(wasteSource);
        return new ResponseEntity<>(newWasteSource, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<WasteSource> update(@PathVariable("id") Long id, @RequestBody WasteSource wasteSource) {
        WasteSource existingWasteSource;
        try {
            existingWasteSource = wasteSourceRepository
                                .findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                    ErrorMessages.getRecordNotFoundMsg("WasteSource", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingWasteSource.setName(wasteSource.getName());
        wasteSourceRepository.save(existingWasteSource);
        return new ResponseEntity<>(existingWasteSource, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<WasteSource> delete(@PathVariable("id") Long id) {
        WasteSource wasteSourceToDelete;
        try {
            wasteSourceToDelete = wasteSourceRepository
                                .findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                    ErrorMessages.getRecordNotFoundMsg("WasteSource", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wasteSourceRepository.delete(wasteSourceToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
