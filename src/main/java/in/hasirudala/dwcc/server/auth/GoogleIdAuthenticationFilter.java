package in.hasirudala.dwcc.server.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class GoogleIdAuthenticationFilter extends OncePerRequestFilter {
    private static final String authHeader = "Authorization";
    private static final String authHeaderPrefix = "Bearer ";

    private AuthenticationDetailsSource<HttpServletRequest, ?>
        authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private HttpTransport httpTransport = new ApacheHttpTransport();
    private static JsonFactory jsonFactory = new JacksonFactory();

    @Value("${google.identityClientId}")
    private String clientId;

    @Value("${google.allowedHostedDomain}")
    private String allowedHostedDomain;

    @Resource
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    public GoogleIdAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(authHeader);

        if(header == null || !header.startsWith(authHeaderPrefix)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(authHeaderPrefix, "");

        Object details = this.authenticationDetailsSource.buildDetails(request);

        try {
            GoogleIdAuthenticationToken
                googleIdAuthenticationToken = new GoogleIdAuthenticationToken(token, details);

            GoogleIdToken googleIdToken = getVerifiedToken(googleIdAuthenticationToken);

            UserDetails userDetails = getUserDetails(googleIdToken);

            Authentication auth = new GoogleIdAuthenticationToken(
                (String) googleIdAuthenticationToken.getCredentials(),
                userDetails.getUsername(),
                userDetails.getAuthorities(),
                googleIdAuthenticationToken.getDetails()
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            if (this.logger.isErrorEnabled())
                this.logger.error(e.getMessage());

            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

    private GoogleIdToken getVerifiedToken(GoogleIdAuthenticationToken idAuthenticationToken) throws BadCredentialsException {
        GoogleIdTokenVerifier
            verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
            .setAudience(Collections.singletonList(getClientId()))
            .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify((String) idAuthenticationToken.getCredentials());
            if (idToken == null) {
                throw new BadCredentialsException("Verification failed. Got expired or invalid token.");
            }
            validateDomain(idToken);

        } catch (IOException | GeneralSecurityException e) {
            if (this.logger.isErrorEnabled()) this.logger.error(e.getMessage());
            throw new AuthenticationServiceException("Unable to verify token", e);
        }
        return idToken;
    }

    private UserDetails getUserDetails(GoogleIdToken idToken) {
        return userDetailsService.loadUserByUsername(idToken.getPayload().getEmail());
    }

    private void validateDomain(GoogleIdToken idToken) {
        Payload payload = idToken.getPayload();
        if (!allowedHostedDomain.equals(payload.getHostedDomain())) {
            throw new BadCredentialsException(
                String.format("Invalid token. %s is not part of %s organization",
                    payload.getEmail(), allowedHostedDomain));
        }
    }

    private String getClientId() { return clientId; }
}
