package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.OutgoingWasteRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.sql.Date;


@Repository
@RepositoryRestResource(collectionResourceRel = "outgoingWaste", path = "outgoingWaste")
public interface OutgoingWasteRecordRepository extends JpaRepository<OutgoingWasteRecord, Long> {
    @Query(value = "SELECT * FROM outgoing_waste_records " +
                           "WHERE extract (MONTH FROM to_date) = :m " +
                           "AND extract (YEAR FROM to_date) = :y " +
                           "AND dwcc_id = :dwccId " +
                           "ORDER BY from_date", nativeQuery = true)
    Page<OutgoingWasteRecord> getDwccRecordsByMonthYear(
            @Param("m") Integer m,
            @Param("y") Integer y,
            @Param("dwccId") Integer dwccId,
            Pageable pageable
    );

    @Query(value = "SELECT EXISTS(SELECT 1 FROM outgoing_waste_records " +
                           "WHERE dwcc_id = :dwccId " +
                           "AND ((:from BETWEEN from_date AND to_date) " +
                           "  OR (:to BETWEEN from_date AND to_date) " +
                           "  OR ((from_date BETWEEN :from AND :to) AND (to_date BETWEEN :from AND :to))))",
                           nativeQuery = true)
    boolean dateRangeExists(@Param("from") Date from, @Param("to") Date to, @Param("dwccId") Long dwccId);
}
