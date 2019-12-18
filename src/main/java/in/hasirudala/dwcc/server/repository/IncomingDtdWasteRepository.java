package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.IncomingDtdWaste;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
//@RepositoryRestResource(collectionResourceRel = "incomingDtd", path = "incomingDtd")
public interface IncomingDtdWasteRepository extends JpaRepository<IncomingDtdWaste, Long> {
}
