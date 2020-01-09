package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.OutgoingWasteEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutgoingWasteEntryRepository extends JpaRepository<OutgoingWasteEntry, Long> {
}
