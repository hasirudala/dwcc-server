package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.Dwcc;
import in.hasirudala.dwcc.server.repository.DwccRepository;
import in.hasirudala.dwcc.server.repository.WardRepository;
import in.hasirudala.dwcc.server.web.contract.DwccRequest;
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
@RequestMapping("/api/dwccs")
public class DwccController {
    private DwccRepository dwccRepository;
    private WardRepository wardRepository;

    public DwccController(DwccRepository dwccRepository, WardRepository wardRepository) {
        this.dwccRepository = dwccRepository;
        this.wardRepository = wardRepository;
    }

    @GetMapping
    public Page<Dwcc> listDwccs(Pageable pageable) { return dwccRepository.findAll(pageable); }

    @GetMapping("/{id}")
    public ResponseEntity<Dwcc> getDwcc(@PathVariable("id") Long id) {
        Dwcc dwcc;
        try {
            dwcc = dwccRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("Dwcc", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dwcc, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<Dwcc> create(@RequestBody DwccRequest payload) {
        Dwcc newDwcc = new Dwcc();
        newDwcc.setName(payload.getName());
        newDwcc.setWard(wardRepository.getOne(payload.getWardId()));
        newDwcc.setOwnerOrAgencyName(payload.getOwnerOrAgencyName());
        newDwcc.setOwnedAndOperated(payload.getOwnedAndOperated());
        newDwcc.setOperatingSince(payload.getOperatingSince());
        newDwcc.setAreaInSqFt(payload.getAreaInSqFt());
        newDwcc.setMouMoaSigned(payload.getMouMoaSigned());
        newDwcc.assignUuid();
        dwccRepository.save(newDwcc);
        return new ResponseEntity<>(newDwcc, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Dwcc> update(@PathVariable("id") Long id, @RequestBody DwccRequest payload) {
        Dwcc existingDwcc;
        try {
            existingDwcc = dwccRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                    ErrorMessages.getRecordNotFoundMsg("Dwcc", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingDwcc.setName(payload.getName());
        existingDwcc.setWard(wardRepository.getOne(payload.getWardId()));
        existingDwcc.setOwnerOrAgencyName(payload.getOwnerOrAgencyName());
        existingDwcc.setOwnedAndOperated(payload.getOwnedAndOperated());
        existingDwcc.setOperatingSince(payload.getOperatingSince());
        existingDwcc.setAreaInSqFt(payload.getAreaInSqFt());
        existingDwcc.setMouMoaSigned(payload.getMouMoaSigned());
        dwccRepository.save(existingDwcc);
        return new ResponseEntity<>(existingDwcc, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Dwcc> delete(@PathVariable("id") Long id) {
        Dwcc dwccToDelete;
        try {
            dwccToDelete = dwccRepository
                                .findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                    ErrorMessages.getRecordNotFoundMsg("Dwcc", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dwccRepository.delete(dwccToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
