package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.ExpenseTag;
import in.hasirudala.dwcc.server.repository.ExpenseTagRepository;
import in.hasirudala.dwcc.server.repository.ExpenseTypeRepository;
import in.hasirudala.dwcc.server.web.contract.ExpenseTagRequest;
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
@RequestMapping("/api/expenseTags")
public class ExpenseTagController {
    private ExpenseTagRepository expenseTagRepository;
    private ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTagController(ExpenseTagRepository expenseTagRepository,
                                ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTagRepository = expenseTagRepository;
        this.expenseTypeRepository = expenseTypeRepository;
    }

    @GetMapping
    public Page<ExpenseTag> listExpenseTags(Pageable pageable) {
        return expenseTagRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseTag> getExpenseTag(@PathVariable("id") Long id) {
        ExpenseTag tag;
        try {
            tag = expenseTagRepository
                          .findById(id)
                          .orElseThrow(() -> new NoSuchElementException(
                                  ErrorMessages.getRecordNotFoundMsg("ExpenseTag", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<ExpenseTag> create(@RequestBody ExpenseTagRequest payload) {
        ExpenseTag tag = new ExpenseTag();
        tag.assignUuid();
        tag.setName(payload.getName());
        if (payload.getTypeId() != null)
            tag.setType(expenseTypeRepository.getOne(payload.getTypeId()));
        expenseTagRepository.save(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ExpenseTag> update(@PathVariable("id") Long id, @RequestBody ExpenseTagRequest payload) {
        ExpenseTag existingTag;
        try {
            existingTag = expenseTagRepository
                                  .findById(id)
                                  .orElseThrow(() -> new NoSuchElementException(
                                          ErrorMessages.getRecordNotFoundMsg("ExpenseTag", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingTag.setName(payload.getName());
        existingTag.setType(expenseTypeRepository.getOne(payload.getTypeId()));
        expenseTagRepository.save(existingTag);
        return new ResponseEntity<>(existingTag, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ExpenseTag> delete(@PathVariable("id") Long id) {
        ExpenseTag tagToDelete;
        try {
            tagToDelete = expenseTagRepository
                                  .findById(id)
                                  .orElseThrow(() -> new NoSuchElementException(
                                          ErrorMessages.getRecordNotFoundMsg("ExpenseTag", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        expenseTagRepository.delete(tagToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
