package in.hasirudala.dwcc.server.config;

import com.bugsnag.Bugsnag;
import com.bugsnag.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> reportUnhandledException(Exception e, HttpServletResponse response) throws IOException {
        Report report = bugsnag.buildReport(e);
        bugsnag.notify(report);
        logger.error(e.getMessage(), e);
        if (e instanceof DataIntegrityViolationException) {
            response.sendError(BadRequest.value());
        }
        else if (e instanceof MethodArgumentNotValidException) {
            Map<String, Object> body = buildMANVResponseBody((MethodArgumentNotValidException) e);
            return new ResponseEntity<>(body, BadRequest);
        }
        return ResponseEntity.status(InternalServerError).body(e.getMessage());
    }

    private Map<String, Object> buildMANVResponseBody(MethodArgumentNotValidException e) {
        Map<String, Object> body = new LinkedHashMap<>();

        List<String> errors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.toList());

        body.put("timestamp", new Date());
        body.put("status", BadRequest.value());
        body.put("errors", errors);
        return body;
    }
}