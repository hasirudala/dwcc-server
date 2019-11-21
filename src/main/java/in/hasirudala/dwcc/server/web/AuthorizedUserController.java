package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.AuthorizedUser;
import in.hasirudala.dwcc.server.service.AuthorizedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class AuthorizedUserController {
    private final AuthorizedUserService userService;

    @Autowired
    public AuthorizedUserController(AuthorizedUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AuthorizedUser> createUser(@RequestBody AuthorizedUser user) {
        AuthorizedUser newUser = userService.create(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public Page<AuthorizedUser> listUsers(Pageable pageable) {
        return userService.getNonDeletedUsers(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorizedUser> getUser(@PathVariable("id") Long id) {
        AuthorizedUser user;
        try {
            user = userService.getUserById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = { RequestMethod.PUT, RequestMethod.PATCH })
    @Transactional
    public ResponseEntity<AuthorizedUser> updateUser(@PathVariable("id") Long id, @Valid @RequestBody AuthorizedUser user) {
        AuthorizedUser updatedUser;
        try {
            updatedUser = userService.update(id, user);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
