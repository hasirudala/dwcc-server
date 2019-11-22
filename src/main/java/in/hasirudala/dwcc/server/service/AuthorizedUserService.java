package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.AuthorizedUser;
import in.hasirudala.dwcc.server.repository.AuthorizedUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

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
        user.assignUuid();
        return userRepository.save(user);
    }

    public Page<AuthorizedUser> getNonDeletedUsers(Pageable pageable) {
        return userRepository.findAllByIsFauxDeletedFalse(pageable);
    }

    public AuthorizedUser getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(getUserNotFoundMsg(id)));
    }

    public AuthorizedUser update(Long id, AuthorizedUser user) {
        AuthorizedUser
            existingUser = userRepository
                            .findById(id)
                            .orElseThrow(() -> new NoSuchElementException(getUserNotFoundMsg(id)));
        existingUser.setName(user.getName());
        existingUser.setAdmin(user.isAdmin());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        AuthorizedUser
            existingUser = userRepository
                            .findById(id)
                            .orElseThrow(() -> new NoSuchElementException(getUserNotFoundMsg(id)));
        existingUser.setFauxDeleted(true);
        // on setting fauxDeleted to true, a trigger will append
        // [deleted on <time>] string to this user's "email" field.
        // https://git.io/Je6aG
        userRepository.save(existingUser);
    }

    public AuthorizedUser getCurrentlyLoggedInUser() {
        String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (userRepository.findByEmailAndIsFauxDeletedFalse(emailId));
    }

    private String getUserNotFoundMsg(Long id) {
        return String.format("User with id %d doesn't exist", id);
    }
}
