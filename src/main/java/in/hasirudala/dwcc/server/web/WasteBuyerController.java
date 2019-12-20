package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.WasteBuyer;
import in.hasirudala.dwcc.server.repository.WasteBuyerRepository;
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
@RequestMapping("/api/wasteBuyers")
public class WasteBuyerController {
    private WasteBuyerRepository wasteBuyerRepository;

    public WasteBuyerController(WasteBuyerRepository wasteBuyerRepository) {
        this.wasteBuyerRepository = wasteBuyerRepository;
    }

    @GetMapping
    public Page<WasteBuyer> listWasteBuyers(Pageable pageable) {
        return wasteBuyerRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteBuyer> getWasteBuyer(@PathVariable("id") Long id) {
        WasteBuyer wasteBuyer;
        try {
            wasteBuyer = wasteBuyerRepository
                                 .findById(id)
                                 .orElseThrow(() -> new NoSuchElementException(
                                         ErrorMessages.getRecordNotFoundMsg("WasteBuyer", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(wasteBuyer, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<WasteBuyer> create(@RequestBody WasteBuyer wasteBuyer) {
        wasteBuyer.assignUuid();
        WasteBuyer newWasteBuyer = wasteBuyerRepository.save(wasteBuyer);
        return new ResponseEntity<>(newWasteBuyer, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<WasteBuyer> update(@PathVariable("id") Long id, @RequestBody WasteBuyer wasteBuyer) {
        WasteBuyer existingWasteBuyer;
        try {
            existingWasteBuyer = wasteBuyerRepository
                                         .findById(id)
                                         .orElseThrow(() -> new NoSuchElementException(
                                                 ErrorMessages.getRecordNotFoundMsg("WasteBuyer", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingWasteBuyer.setName(wasteBuyer.getName());
        wasteBuyerRepository.save(existingWasteBuyer);
        return new ResponseEntity<>(existingWasteBuyer, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<WasteBuyer> delete(@PathVariable("id") Long id) {
        WasteBuyer wasteBuyerToDelete;
        try {
            wasteBuyerToDelete = wasteBuyerRepository
                                         .findById(id)
                                         .orElseThrow(() -> new NoSuchElementException(
                                                 ErrorMessages.getRecordNotFoundMsg("WasteBuyer", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wasteBuyerRepository.delete(wasteBuyerToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
