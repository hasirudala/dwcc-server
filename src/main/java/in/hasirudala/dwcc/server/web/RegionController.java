package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.Region;
import in.hasirudala.dwcc.server.repository.RegionRepository;
import in.hasirudala.dwcc.server.web.messages.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/regions")
public class RegionController {
    private RegionRepository regionRepository;

    public RegionController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @GetMapping
    public Page<Region> listRegions(Pageable pageable) {
        return regionRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegion(@PathVariable("id") Long id) {
        Region region;
        try {
            region = regionRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("Region", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(region, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Region> create(@RequestBody Region region) {
        region.assignUuid();
        Region newRegion = regionRepository.save(region);
        return new ResponseEntity<>(newRegion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Region> update(@PathVariable("id") Long id, @RequestBody Region region) {
        Region existingRegion;
        try {
            existingRegion = regionRepository
                                .findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                    ErrorMessages.getRecordNotFoundMsg("Region", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingRegion.setName(region.getName());
        regionRepository.save(existingRegion);
        return new ResponseEntity<>(existingRegion, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Region> delete(@PathVariable("id") Long id) {
        Region regionToDelete;
        try {
            regionToDelete = regionRepository
                                .findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                    ErrorMessages.getRecordNotFoundMsg("Region", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        regionRepository.delete(regionToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
