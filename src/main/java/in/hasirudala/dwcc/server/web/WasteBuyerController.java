package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.WasteBuyer;
import in.hasirudala.dwcc.server.repository.RegionRepository;
import in.hasirudala.dwcc.server.repository.WasteBuyerRepository;
import in.hasirudala.dwcc.server.web.contract.WasteBuyerRequest;
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
@RequestMapping("/api/wasteBuyers")
public class WasteBuyerController {
    private WasteBuyerRepository wasteBuyerRepository;
    private RegionRepository regionRepository;

    @Autowired
    public WasteBuyerController(WasteBuyerRepository wasteBuyerRepository,
                                RegionRepository regionRepository) {
        this.wasteBuyerRepository = wasteBuyerRepository;
        this.regionRepository = regionRepository;
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
    public ResponseEntity<WasteBuyer> create(@RequestBody WasteBuyerRequest payload) {
        WasteBuyer newWasteBuyer = new WasteBuyer();
        newWasteBuyer.assignUuid();
        newWasteBuyer.setName(payload.getName());
        newWasteBuyer.setRegion(regionRepository.getOne(payload.getRegionId()));
        wasteBuyerRepository.save(newWasteBuyer);
        return new ResponseEntity<>(newWasteBuyer, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<WasteBuyer> update(@PathVariable("id") Long id, @RequestBody WasteBuyer payload) {
        WasteBuyer existingWasteBuyer;
        try {
            existingWasteBuyer = wasteBuyerRepository
                                         .findById(id)
                                         .orElseThrow(() -> new NoSuchElementException(
                                                 ErrorMessages.getRecordNotFoundMsg("WasteBuyer", id)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingWasteBuyer.setName(payload.getName());
        existingWasteBuyer.setRegion(regionRepository.getOne(payload.getRegionId()));
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
