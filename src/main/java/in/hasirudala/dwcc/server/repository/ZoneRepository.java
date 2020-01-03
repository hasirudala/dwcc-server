package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RepositoryRestResource(collectionResourceRel = "zones", path = "zones")
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    @RestResource(path = "findAllById", rel = "findAllById")
    List<Zone> findByIdIn(@Param("ids") Long[] ids);
}
