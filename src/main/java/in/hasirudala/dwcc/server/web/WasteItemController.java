package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.WasteItem;
import in.hasirudala.dwcc.server.domain.WasteTag;
import in.hasirudala.dwcc.server.domain.WasteType;
import in.hasirudala.dwcc.server.repository.WasteItemRepository;
import in.hasirudala.dwcc.server.repository.WasteTagRepository;
import in.hasirudala.dwcc.server.repository.WasteTypeRepository;
import in.hasirudala.dwcc.server.web.contract.WasteItemRequest;
import in.hasirudala.dwcc.server.web.messages.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wasteItems")
public class WasteItemController {
    private WasteItemRepository wasteItemRepository;
    private WasteTypeRepository wasteTypeRepository;
    private WasteTagRepository wasteTagRepository;

    public WasteItemController(WasteItemRepository wasteItemRepository,
                               WasteTypeRepository wasteTypeRepository,
                               WasteTagRepository wasteTagRepository) {
        this.wasteItemRepository = wasteItemRepository;
        this.wasteTypeRepository = wasteTypeRepository;
        this.wasteTagRepository = wasteTagRepository;
    }

    @GetMapping
    public Page<WasteItem> listWasteItems(Pageable pageable) {
        return wasteItemRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteItem> getWasteItem(@PathVariable("id") Long id) {
        WasteItem item;
        try {
            item = wasteItemRepository
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("WasteItem", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<WasteItem> create(@RequestBody WasteItemRequest payload) {
        WasteItem newItem = new WasteItem();
        newItem.assignUuid();
        newItem.setName(payload.getName());

        WasteType type = null;
        if (payload.getTypeId() != null) {
            type = wasteTypeRepository.getOne(payload.getTypeId());
            newItem.setType(type);
        }

        newItem.setTags(getTagsFilteredByType(payload.getTagIds(), type));

        wasteItemRepository.save(newItem);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    private Set<WasteTag> getTagsFilteredByType(Set<Long> tagIds, WasteType type) {
        if (tagIds.isEmpty())
            return new HashSet<>();
        Long[] _tagIds = tagIds.toArray(new Long[]{});
        Set<WasteTag> tags = new HashSet<>(wasteTagRepository.findByIdIn(_tagIds));
        return tags.stream()
                .filter(tag -> tag.getType() == null || tag.getType().equals(type))
                .collect(Collectors.toSet());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<WasteItem> update(@PathVariable("id") Long id, @RequestBody WasteItemRequest payload) {
        WasteItem existingItem;
        try {
            existingItem = wasteItemRepository
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("WasteItem", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingItem.setName(payload.getName());

        WasteType type = null;
        if (payload.getTypeId() == null)
            existingItem.setType(null);
        else {
            type = wasteTypeRepository.getOne(payload.getTypeId());
            existingItem.setType(type);
        }

        existingItem.setTags(getTagsFilteredByType(payload.getTagIds(), type));

        wasteItemRepository.save(existingItem);
        return new ResponseEntity<>(existingItem, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<WasteItem> delete(@PathVariable("id") Long id) {
        WasteItem itemToDelete;
        try {
            itemToDelete = wasteItemRepository
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("WasteItem", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wasteItemRepository.delete(itemToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
