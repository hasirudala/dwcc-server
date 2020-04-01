package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.AuthorizedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface AuthorizedUserRepository extends JpaRepository<AuthorizedUser, Long> {
    AuthorizedUser findByEmail(String email);

    @RestResource(path = "find", rel = "find")
    Page<AuthorizedUser> findByEmailIgnoreCaseStartingWithOrderByEmailAsc(@Param("email") String email, Pageable pageable);
}
