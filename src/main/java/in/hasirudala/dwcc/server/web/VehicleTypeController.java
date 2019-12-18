package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.VehicleType;
import in.hasirudala.dwcc.server.repository.VehicleTypeRepository;
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
@RequestMapping("/api/vehicleTypes")
public class VehicleTypeController {
    private VehicleTypeRepository vehicleTypeRepository;

    public VehicleTypeController(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    @GetMapping
    public Page<VehicleType> listVehicleTypes(Pageable pageable) {
        return vehicleTypeRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleType> getVehicleType(@PathVariable("id") Long id) {
        VehicleType vehicleType;
        try {
            vehicleType = vehicleTypeRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException(
                            ErrorMessages.getRecordNotFoundMsg("VehicleType", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehicleType, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<VehicleType> create(@RequestBody VehicleType vehicleType) {
        vehicleType.assignUuid();
        VehicleType newVehicleType = vehicleTypeRepository.save(vehicleType);
        return new ResponseEntity<>(newVehicleType, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<VehicleType> update(@PathVariable("id") Long id, @RequestBody VehicleType vehicleType) {
        VehicleType existingVehicleType;
        try {
            existingVehicleType = vehicleTypeRepository
                                .findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                    ErrorMessages.getRecordNotFoundMsg("VehicleType", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingVehicleType.setName(vehicleType.getName());
        vehicleTypeRepository.save(existingVehicleType);
        return new ResponseEntity<>(existingVehicleType, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<VehicleType> delete(@PathVariable("id") Long id) {
        VehicleType vehicleTypeToDelete;
        try {
            vehicleTypeToDelete = vehicleTypeRepository
                                .findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                    ErrorMessages.getRecordNotFoundMsg("VehicleType", id)));
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        vehicleTypeRepository.delete(vehicleTypeToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
