package in.hasirudala.dwcc.server.repository;

import in.hasirudala.dwcc.server.domain.AuthorizedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizedUserRepository extends JpaRepository<AuthorizedUser, Long> {

    AuthorizedUser findByEmail(String email);

}
