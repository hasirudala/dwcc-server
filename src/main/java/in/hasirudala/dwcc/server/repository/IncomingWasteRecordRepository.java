package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.IncomingWasteRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@Repository
@RepositoryRestResource(collectionResourceRel = "incomingWaste", path = "incomingWaste")
public interface IncomingWasteRecordRepository extends JpaRepository<IncomingWasteRecord, Long> {
    @Query(value =
            "SELECT * FROM incoming_waste_records " +
            "WHERE extract (MONTH FROM date) = :m AND extract (YEAR FROM date) = :y AND dwcc_id = :dwccId",
            nativeQuery = true)
    Page<IncomingWasteRecord> getDwccRecordsByMonthYear(
            @Param("m") Integer m,
            @Param("y") Integer y,
            @Param("dwccId") Integer dwccId,
            Pageable pageable
    );
}
