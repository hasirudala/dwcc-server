package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.AuthorizedUser;
import in.hasirudala.dwcc.server.repository.AuthorizedUserRepository;
import in.hasirudala.dwcc.server.web.messages.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthorizedUserService {
    private AuthorizedUserRepository userRepository;

    @Autowired
    public AuthorizedUserService(AuthorizedUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthorizedUser create(AuthorizedUser user) {
        user.assignUuid();
        return userRepository.save(user);
    }

    public Page<AuthorizedUser> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public AuthorizedUser getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                    ErrorMessages.getRecordNotFoundMsg("User", id)));
    }

    public AuthorizedUser update(Long id, AuthorizedUser user) {
        AuthorizedUser
            existingUser = userRepository
                            .findById(id)
                            .orElseThrow(() -> new NoSuchElementException(
                                ErrorMessages.getRecordNotFoundMsg("User", id)));
        existingUser.setName(user.getName());
        existingUser.setAdmin(user.isAdmin());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        AuthorizedUser
            existingUser = userRepository
                            .findById(id)
                            .orElseThrow(() -> new NoSuchElementException(
                                ErrorMessages.getRecordNotFoundMsg("User", id)));
        userRepository.delete(existingUser);
    }

    public AuthorizedUser getCurrentlyLoggedInUser() {
        String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (userRepository.findByEmail(emailId));
    }
}
