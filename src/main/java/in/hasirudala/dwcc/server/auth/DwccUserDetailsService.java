package in.hasirudala.dwcc.server.auth;

import in.hasirudala.dwcc.server.domain.AuthorizedUser;
import in.hasirudala.dwcc.server.repository.AuthorizedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("userDetailsService")
public class DwccUserDetailsService implements UserDetailsService {

    private AuthorizedUserRepository userRepository;

    @Autowired
    public DwccUserDetailsService(AuthorizedUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthorizedUser user = userRepository.findByEmailAndIsDisabledFalseAndIsFauxDeletedFalse(email);

        if (user == null)
            throw new UsernameNotFoundException(String.format("User with email %s not found", email));

        List<SimpleGrantedAuthority>
            authorities = Collections.singletonList(new SimpleGrantedAuthority(user.isAdmin() ? "ADMIN" : "USER"));

        return new User(user.getEmail(), "", authorities);
    }
}
