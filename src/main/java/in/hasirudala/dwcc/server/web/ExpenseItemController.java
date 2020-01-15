package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.ExpenseItem;
import in.hasirudala.dwcc.server.domain.ExpenseTag;
import in.hasirudala.dwcc.server.domain.ExpenseType;
import in.hasirudala.dwcc.server.repository.ExpenseItemRepository;
import in.hasirudala.dwcc.server.repository.ExpenseTagRepository;
import in.hasirudala.dwcc.server.repository.ExpenseTypeRepository;
import in.hasirudala.dwcc.server.web.contract.ExpenseItemRequest;
import in.hasirudala.dwcc.server.web.messages.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expenseItems")
public class ExpenseItemController {
    private ExpenseItemRepository expenseItemRepository;
    private ExpenseTypeRepository expenseTypeRepository;
    private ExpenseTagRepository expenseTagRepository;

    public ExpenseItemController(ExpenseItemRepository expenseItemRepository,
                                 ExpenseTypeRepository expenseTypeRepository,
                                 ExpenseTagRepository expenseTagRepository) {
        this.expenseItemRepository = expenseItemRepository;
        this.expenseTypeRepository = expenseTypeRepository;
        this.expenseTagRepository = expenseTagRepository;
    }

    @GetMapping
    public Page<ExpenseItem> listExpenseItems(Pageable pageable) {
        return expenseItemRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseItem> getExpenseItem(@PathVariable("id") Long id) {
        ExpenseItem item;
        try {
            item = expenseItemRepository
                           .findById(id)
                           .orElseThrow(() -> new NoSuchElementException(
                                   ErrorMessages.getRecordNotFoundMsg("ExpenseItem", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<ExpenseItem> create(@RequestBody ExpenseItemRequest payload) {
        ExpenseItem newItem = new ExpenseItem();
        newItem.assignUuid();
        newItem.setName(payload.getName());

        ExpenseType type = null;
        if (payload.getTypeId() != null) {
            type = expenseTypeRepository.getOne(payload.getTypeId());
            newItem.setType(type);
        }

        newItem.setTags(getTagsFilteredByType(payload.getTagIds(), type));
        newItem.setAskNumberOfUnits(payload.getAskNumberOfUnits());

        expenseItemRepository.save(newItem);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    private Set<ExpenseTag> getTagsFilteredByType(Set<Long> tagIds, ExpenseType type) {
        if (tagIds.isEmpty())
            return new HashSet<>();
        Long[] _tagIds = tagIds.toArray(new Long[]{});
        Set<ExpenseTag> tags = new HashSet<>(expenseTagRepository.findByIdIn(_tagIds));
        return tags.stream()
                       .filter(tag -> tag.getType() == null || tag.getType().equals(type))
                       .collect(Collectors.toSet());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ExpenseItem> update(@PathVariable("id") Long id, @RequestBody ExpenseItemRequest payload) {
        ExpenseItem existingItem;
        try {
            existingItem = expenseItemRepository
                                   .findById(id)
                                   .orElseThrow(() -> new NoSuchElementException(
                                           ErrorMessages.getRecordNotFoundMsg("ExpenseItem", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingItem.setName(payload.getName());
        existingItem.setAskNumberOfUnits(payload.getAskNumberOfUnits());

        ExpenseType type = null;
        if (payload.getTypeId() == null)
            existingItem.setType(null);
        else {
            type = expenseTypeRepository.getOne(payload.getTypeId());
            existingItem.setType(type);
        }

        existingItem.setTags(getTagsFilteredByType(payload.getTagIds(), type));

        expenseItemRepository.save(existingItem);
        return new ResponseEntity<>(existingItem, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ExpenseItem> delete(@PathVariable("id") Long id) {
        ExpenseItem itemToDelete;
        try {
            itemToDelete = expenseItemRepository
                                   .findById(id)
                                   .orElseThrow(() -> new NoSuchElementException(
                                           ErrorMessages.getRecordNotFoundMsg("ExpenseItem", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        expenseItemRepository.delete(itemToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
