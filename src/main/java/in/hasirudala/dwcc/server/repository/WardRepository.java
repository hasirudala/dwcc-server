package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.Ward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "wards", path = "wards")
public interface WardRepository extends JpaRepository<Ward, Long> {
    @RestResource(path = "findAllById", rel = "findAllById")
    List<Ward> findByIdIn(@Param("ids") Long[] ids);

    @RestResource(path = "find", rel = "find")
    Page<Ward> findByNameIgnoreCaseStartingWithOrderByNameAsc(@Param("name") String name, Pageable pageable);
}
