package in.hasirudala.dwcc.server.web;

import in.hasirudala.dwcc.server.domain.AuthorizedUser;
import in.hasirudala.dwcc.server.service.AuthorizedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthorizedUserController {
    private final AuthorizedUserService userService;

    @Autowired
    public AuthorizedUserController(AuthorizedUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AuthorizedUser> createUser(@RequestBody AuthorizedUser user) {
        AuthorizedUser _user = userService.create(user);
        return new ResponseEntity<>(_user, HttpStatus.CREATED);
    }
}
