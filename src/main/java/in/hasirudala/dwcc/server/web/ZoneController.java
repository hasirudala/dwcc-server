package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.Zone;
import in.hasirudala.dwcc.server.repository.RegionRepository;
import in.hasirudala.dwcc.server.repository.ZoneRepository;
import in.hasirudala.dwcc.server.web.contract.ZoneRequest;
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
@RequestMapping("/api/zones")
public class ZoneController {
    private ZoneRepository zoneRepository;
    private RegionRepository regionRepository;

    public ZoneController(ZoneRepository zoneRepository, RegionRepository regionRepository) {
        this.zoneRepository = zoneRepository;
        this.regionRepository = regionRepository;
    }

    @GetMapping
    public Page<Zone> listZones(Pageable pageable) { return zoneRepository.findAll(pageable); }

    @GetMapping("/{id}")
    public ResponseEntity<Zone> getZone(@PathVariable("id") Long id) {
        Zone zone;
        try {
            zone = zoneRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("Zone", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(zone, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<Zone> create(@RequestBody ZoneRequest payload) {
        Zone newZone = new Zone();
        newZone.setName(payload.getName());
        newZone.setRegion(regionRepository.getOne(payload.getRegionId()));
        newZone.assignUuid();
        zoneRepository.save(newZone);
        return new ResponseEntity<>(newZone, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Zone> update(@PathVariable("id") Long id, @RequestBody ZoneRequest payload) {
        Zone existingZone;
        try {
            existingZone = zoneRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                    ErrorMessages.getRecordNotFoundMsg("Zone", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingZone.setName(payload.getName());
        existingZone.setRegion(regionRepository.getOne(payload.getRegionId()));
        zoneRepository.save(existingZone);
        return new ResponseEntity<>(existingZone, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Zone> delete(@PathVariable("id") Long id) {
        Zone zoneToDelete;
        try {
            zoneToDelete = zoneRepository
                                .findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                    ErrorMessages.getRecordNotFoundMsg("Zone", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        zoneRepository.delete(zoneToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
