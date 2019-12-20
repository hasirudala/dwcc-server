package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.OutgoingWasteRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@Repository
@RepositoryRestResource(collectionResourceRel = "outgoingWaste", path = "outgoingWaste")
public interface OutgoingWasteRecordRepository extends JpaRepository<OutgoingWasteRecord, Long> {
    @Query(value =
            "SELECT * FROM outgoing_waste_records " +
            "WHERE extract (MONTH FROM date) = :m AND extract (YEAR FROM date) = :y AND dwcc_id = :dwccId",
            nativeQuery = true)
    Page<OutgoingWasteRecord> getDwccRecordsByMonthYear(
            @Param("m") Integer m,
            @Param("y") Integer y,
            @Param("dwccId") Integer dwccId,
            Pageable pageable
    );
}
