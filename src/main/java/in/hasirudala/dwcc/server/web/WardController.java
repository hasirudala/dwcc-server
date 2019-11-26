package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.Ward;
import in.hasirudala.dwcc.server.repository.RegionRepository;
import in.hasirudala.dwcc.server.repository.WardRepository;
import in.hasirudala.dwcc.server.web.contract.WardRequestContract;
import in.hasirudala.dwcc.server.web.messages.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/wards")
public class WardController {
    private WardRepository wardRepository;
    private RegionRepository regionRepository;

    public WardController(WardRepository wardRepository, RegionRepository regionRepository) {
        this.wardRepository = wardRepository;
        this.regionRepository = regionRepository;
    }

    @GetMapping
    public Page<Ward> listWards(Pageable pageable) { return wardRepository.findAll(pageable); }

    @GetMapping("/{id}")
    public ResponseEntity<Ward> getWard(@PathVariable("id") Long id) {
        Ward ward;
        try {
            ward = wardRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("Ward", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ward, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Ward> create(@RequestBody WardRequestContract payload) {
        Ward newWard = new Ward();
        newWard.setName(payload.getName());
        newWard.setRegion(regionRepository.getOne(payload.getRegionId()));
        newWard.assignUuid();
        wardRepository.save(newWard);
        return new ResponseEntity<>(newWard, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Ward> update(@PathVariable("id") Long id, @RequestBody WardRequestContract payload) {
        Ward existingWard;
        try {
            existingWard = wardRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                    ErrorMessages.getRecordNotFoundMsg("Ward", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingWard.setName(payload.getName());
        existingWard.setRegion(regionRepository.getOne(payload.getRegionId()));
        wardRepository.save(existingWard);
        return new ResponseEntity<>(existingWard, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Ward> delete(@PathVariable("id") Long id) {
        Ward wardToDelete;
        try {
            wardToDelete = wardRepository
                                .findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                    ErrorMessages.getRecordNotFoundMsg("Ward", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wardRepository.delete(wardToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
