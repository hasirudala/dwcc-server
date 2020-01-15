package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.ExpenseRecord;
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
@RepositoryRestResource(collectionResourceRel = "expenses", path = "expenses")
public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecord, Long> {
    @Query(value =
                   "SELECT * FROM expense_records " +
                           "WHERE extract (MONTH FROM date) = :m AND extract (YEAR FROM date) = :y " +
                           "AND dwcc_id = :dwccId",
            nativeQuery = true)
    Page<ExpenseRecord> getDwccRecordsByMonthYear(
            @Param("m") Integer m,
            @Param("y") Integer y,
            @Param("dwccId") Integer dwccId,
            Pageable pageable
    );

    @RestResource(path = "existsForDate", rel = "existsForDate")
    boolean existsByDateAndDwcc_Id(@Param("date") Date date, @Param("dwccId") Long dwccId);
}
