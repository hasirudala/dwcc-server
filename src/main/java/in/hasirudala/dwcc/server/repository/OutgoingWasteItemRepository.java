package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.OutgoingWasteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutgoingWasteItemRepository extends JpaRepository<OutgoingWasteItem, Long> {
}
