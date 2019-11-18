package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.AuthorizedUser;
import in.hasirudala.dwcc.server.repository.AuthorizedUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<AuthorizedUser> getUsersList(Pageable pageable) {
        return userRepository.findAllByIsFauxDeletedFalse(pageable);
    }

    public AuthorizedUser getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                    String.format("User with id %d doesn't exist", id)));
    }

    public void update(Long id, AuthorizedUser user) {
        AuthorizedUser
            existingUser = userRepository
                            .findById(id)
                            .orElseThrow(() -> new NoSuchElementException(
                                String.format("User with id %d doesn't exist", id)));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAdmin(user.isAdmin());
        existingUser.setDisabled(user.isDisabled());
        userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        AuthorizedUser
            existingUser = userRepository
                            .findById(id)
                            .orElseThrow(() -> new NoSuchElementException(
                                String.format("User with id %d doesn't exist", id)));

        existingUser.setFauxDeleted(true);
        userRepository.save(existingUser);
    }
}