package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "wards", path = "wards")
public interface WardRepository extends JpaRepository<Ward, Long> {
}
