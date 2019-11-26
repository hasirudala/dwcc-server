package in.hasirudala.dwcc.server.framework;

import com.bugsnag.Bugsnag;
import com.bugsnag.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorInterceptor {
    private final Logger logger;
    private final Bugsnag bugsnag;

    private static final HttpStatus BadRequest = HttpStatus.BAD_REQUEST;
    private static final HttpStatus InternalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

    @Autowired
    public ErrorInterceptor(Bugsnag bugsnag) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.bugsnag = bugsnag;
    }

    @ExceptionHandler({
        DataIntegrityViolationException.class,
        AccessDeniedException.class
    })
    public ResponseEntity<?> handleSomeExceptions(Exception e) {
        logger.error(e.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", InternalServerError.value());
        if (e instanceof DataIntegrityViolationException) {
            try {
                body.put("message", ((DataIntegrityViolationException) e).getRootCause().getMessage());
            }
            catch (NullPointerException e2) {
                body.put("message", e.getCause().getMessage());
            }
        }
        else {
            body.put("message", e.getMessage());
        }
        return ResponseEntity.status(InternalServerError).body(body);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> reportUnhandledException(Exception e) {
        Report report = bugsnag.buildReport(e);
        bugsnag.notify(report);
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(InternalServerError).body(e.getMessage());
    }

}