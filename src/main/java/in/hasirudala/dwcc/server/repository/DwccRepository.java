package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.Dwcc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "dwccs", path = "dwccs")
public interface DwccRepository extends JpaRepository<Dwcc, Long> {
    @RestResource(path = "find", rel = "find")
    Page<Dwcc> findByNameIgnoreCaseStartingWithOrderByNameAsc(@Param("name") String name, Pageable pageable);
}
