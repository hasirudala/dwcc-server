package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.AuthorizedUser;
import in.hasirudala.dwcc.server.repository.AuthorizedUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizedUserService {
    private AuthorizedUserRepository userRepository;
    private static Logger logger = LoggerFactory.getLogger(AuthorizedUserService.class);

    @Autowired
    public AuthorizedUserService(AuthorizedUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthorizedUser create(AuthorizedUser user) {
        logger.info(String.format("Adding user with id \"%s\"", user.getEmail()));
        return userRepository.save(user);
    }
}
