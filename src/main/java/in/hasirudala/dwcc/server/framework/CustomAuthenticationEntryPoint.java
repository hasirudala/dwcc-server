package in.hasirudala.dwcc.server.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Log logger = LogFactory.getLog(CustomAuthenticationEntryPoint.class);

    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {
        if (logger.isDebugEnabled())
            logger.debug("Custom pre-authenticated entry point called. Rejecting access");

        if (ex instanceof AuthenticationServiceException)
            response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT,
                "Request to external authentication service failed");
        else
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
}
