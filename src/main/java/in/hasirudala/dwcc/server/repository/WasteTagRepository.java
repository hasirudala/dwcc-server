package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.WasteTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RepositoryRestResource(collectionResourceRel = "wasteTags", path = "wasteTags")
public interface WasteTagRepository extends JpaRepository<WasteTag, Long> {
    @RestResource(path = "findAllById", rel = "findAllById")
    List<WasteTag> findByIdIn(@Param("ids") Long[] ids);

    @RestResource(path = "find", rel = "find")
    Page<WasteTag> findByNameIgnoreCaseStartingWithOrderByNameAsc(@Param("name") String name, Pageable pageable);
}
