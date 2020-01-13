package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.ExpenseType;
import in.hasirudala.dwcc.server.repository.ExpenseTypeRepository;
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
@RequestMapping("/api/expenseTypes")
public class ExpenseTypeController {
    private ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeController(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    @GetMapping
    public Page<ExpenseType> listExpenseTypes(Pageable pageable) {
        return expenseTypeRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseType> getExpenseType(@PathVariable("id") Long id) {
        ExpenseType expenseType;
        try {
            expenseType = expenseTypeRepository
                                  .findById(id)
                                  .orElseThrow(() -> new NoSuchElementException(
                                          ErrorMessages.getRecordNotFoundMsg("ExpenseType", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(expenseType, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<ExpenseType> create(@RequestBody ExpenseType expenseType) {
        expenseType.assignUuid();
        ExpenseType newExpenseType = expenseTypeRepository.save(expenseType);
        return new ResponseEntity<>(newExpenseType, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ExpenseType> update(@PathVariable("id") Long id, @RequestBody ExpenseType expenseType) {
        ExpenseType existingExpenseType;
        try {
            existingExpenseType = expenseTypeRepository
                                          .findById(id)
                                          .orElseThrow(() -> new NoSuchElementException(
                                                  ErrorMessages.getRecordNotFoundMsg("ExpenseType", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingExpenseType.setName(expenseType.getName());
        expenseTypeRepository.save(existingExpenseType);
        return new ResponseEntity<>(existingExpenseType, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ExpenseType> delete(@PathVariable("id") Long id) {
        ExpenseType expenseTypeToDelete;
        try {
            expenseTypeToDelete = expenseTypeRepository
                                          .findById(id)
                                          .orElseThrow(() -> new NoSuchElementException(
                                                  ErrorMessages.getRecordNotFoundMsg("ExpenseType", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        expenseTypeRepository.delete(expenseTypeToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
