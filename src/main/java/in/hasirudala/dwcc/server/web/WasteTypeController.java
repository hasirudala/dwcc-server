package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.WasteType;
import in.hasirudala.dwcc.server.repository.WasteTypeRepository;
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
@RequestMapping("/api/wasteTypes")
public class WasteTypeController {
    private WasteTypeRepository wasteTypeRepository;

    public WasteTypeController(WasteTypeRepository wasteTypeRepository) {
        this.wasteTypeRepository = wasteTypeRepository;
    }

    @GetMapping
    public Page<WasteType> listWasteTypes(Pageable pageable) {
        return wasteTypeRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteType> getWasteType(@PathVariable("id") Long id) {
        WasteType wasteType;
        try {
            wasteType = wasteTypeRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("WasteType", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(wasteType, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<WasteType> create(@RequestBody WasteType wasteType) {
        wasteType.assignUuid();
        WasteType newWasteType = wasteTypeRepository.save(wasteType);
        return new ResponseEntity<>(newWasteType, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<WasteType> update(@PathVariable("id") Long id, @RequestBody WasteType wasteType) {
        WasteType existingWasteType;
        try {
            existingWasteType = wasteTypeRepository
                                .findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                    ErrorMessages.getRecordNotFoundMsg("WasteType", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingWasteType.setName(wasteType.getName());
        wasteTypeRepository.save(existingWasteType);
        return new ResponseEntity<>(existingWasteType, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<WasteType> delete(@PathVariable("id") Long id) {
        WasteType wasteTypeToDelete;
        try {
            wasteTypeToDelete = wasteTypeRepository
                                .findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                    ErrorMessages.getRecordNotFoundMsg("WasteType", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wasteTypeRepository.delete(wasteTypeToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
