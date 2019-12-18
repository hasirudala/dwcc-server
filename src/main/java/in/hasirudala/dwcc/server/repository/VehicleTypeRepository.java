package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "vehicle_types", path = "vehicle_types")
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    @RestResource(path = "findAllById", rel = "findAllById")
    List<VehicleType> findByIdIn(@Param("ids") Long[] ids);
}
