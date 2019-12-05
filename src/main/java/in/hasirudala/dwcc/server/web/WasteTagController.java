package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.WasteTag;
import in.hasirudala.dwcc.server.repository.WasteTagRepository;
import in.hasirudala.dwcc.server.repository.WasteTypeRepository;
import in.hasirudala.dwcc.server.web.contract.WasteTagRequest;
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
@RequestMapping("/api/wasteTags")
public class WasteTagController {
    private WasteTagRepository wasteTagRepository;
    private WasteTypeRepository wasteTypeRepository;

    public WasteTagController(WasteTagRepository wasteTagRepository, WasteTypeRepository wasteTypeRepository) {
        this.wasteTagRepository = wasteTagRepository;
        this.wasteTypeRepository = wasteTypeRepository;
    }

    @GetMapping
    public Page<WasteTag> listWasteTags(Pageable pageable) {
        return wasteTagRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteTag> getWasteTag(@PathVariable("id") Long id) {
        WasteTag tag;
        try {
            tag = wasteTagRepository
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("WasteTag", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<WasteTag> create(@RequestBody WasteTagRequest payload) {
        WasteTag tag = new WasteTag();
        tag.assignUuid();
        tag.setName(payload.getName());
        if (payload.getTypeId() != null)
            tag.setType(wasteTypeRepository.getOne(payload.getTypeId()));
        wasteTagRepository.save(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<WasteTag> update(@PathVariable("id") Long id, @RequestBody WasteTagRequest payload) {
        WasteTag existingTag;
        try {
            existingTag = wasteTagRepository
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("WasteTag", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingTag.setName(payload.getName());
        existingTag.setType(wasteTypeRepository.getOne(payload.getTypeId()));
        wasteTagRepository.save(existingTag);
        return new ResponseEntity<>(existingTag, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<WasteTag> delete(@PathVariable("id") Long id) {
        WasteTag tagToDelete;
        try {
            tagToDelete = wasteTagRepository
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("WasteTag", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wasteTagRepository.delete(tagToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
