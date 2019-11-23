package in.hasirudala.dwcc.server.framework;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomExceptionTranslationFilter extends ExceptionTranslationFilter {

    private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
    private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
    private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();


    CustomExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
            chain.doFilter(request, response);
            logger.debug("Chain processed normally");
        }
        catch (Exception ex) {
            if (ex instanceof AuthenticationServiceException) {
                handleSpringSecurityException(request, response, chain, (AuthenticationServiceException) ex);
            }
            else {
                Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
                RuntimeException ase = (AuthenticationException) throwableAnalyzer
                    .getFirstThrowableOfType(AuthenticationException.class, causeChain);

                if (ase == null) {
                    ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(
                        AccessDeniedException.class, causeChain);
                }

                if (ase != null) {
                    if (response.isCommitted()) {
                        throw new ServletException("Unable to handle the Spring Security Exception because the response is already committed.", ex);
                    }
                    handleSpringSecurityException(request, response, chain, ase);
                }
                else {
                    if (ex instanceof ServletException) {
                        throw (ServletException) ex;
                    }
                    else if (ex instanceof RuntimeException) {
                        throw (RuntimeException) ex;
                    }
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void handleSpringSecurityException(HttpServletRequest request,
                                               HttpServletResponse response,
                                               FilterChain chain,
                                               RuntimeException exception) throws IOException, ServletException {

        if (exception instanceof AuthenticationServiceException) {
            logger.debug("Authentication Service exception occurred; redirecting to authentication entry point", exception);
            sendStartAuthentication(request, response, chain, (AuthenticationServiceException) exception);
        }
        else if (exception instanceof AuthenticationException) {
            logger.debug("Authentication exception occurred; redirecting to authentication entry point", exception);
            sendStartAuthentication(request, response, chain, (AuthenticationException) exception);
        }
        else if (exception instanceof AccessDeniedException) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authenticationTrustResolver.isAnonymous(authentication) || authenticationTrustResolver.isRememberMe(authentication)) {
                logger.debug("Access is denied (user is " + (authenticationTrustResolver.isAnonymous(authentication) ? "anonymous" : "not fully authenticated") + "); redirecting to authentication entry point", exception);

                sendStartAuthentication(
                    request,
                    response,
                    chain,
                    new InsufficientAuthenticationException(
                        messages.getMessage(
                            "ExceptionTranslationFilter.insufficientAuthentication",
                            "Full authentication is required to access this resource")));
            }
            else {
                logger.debug("Access is denied (user is not anonymous); delegating to AccessDeniedHandler", exception);
                accessDeniedHandler.handle(request, response, (AccessDeniedException) exception);
            }
        }
    }

    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
        protected void initExtractorMap() {
            super.initExtractorMap();

            registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
                public Throwable extractCause(Throwable throwable) {
                    ThrowableAnalyzer.verifyThrowableHierarchy(throwable,
                        ServletException.class);
                    return ((ServletException) throwable).getRootCause();
                }
            });
        }

    }
}
