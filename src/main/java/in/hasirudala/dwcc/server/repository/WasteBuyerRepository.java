package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.WasteBuyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "wasteBuyers", path = "wasteBuyers")
public interface WasteBuyerRepository extends JpaRepository<WasteBuyer, Long> {
    @RestResource(path = "findAllById", rel = "findAllById")
    List<WasteBuyer> findByIdIn(@Param("ids") Long[] ids);

    @RestResource(path = "byRegion", rel = "byRegion")
    List<WasteBuyer> findByRegion_Id(@Param("id") Long id);
}
