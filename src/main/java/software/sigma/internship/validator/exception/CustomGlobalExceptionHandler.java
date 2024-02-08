package software.sigma.internship.validator.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import software.sigma.internship.mongo.filters.exceptions.WrongQueryParamException;

import java.util.Date;
import java.util.LinkedHashMap;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";

    @ExceptionHandler({UserNotFoundException.class, TestNotFoundException.class,
            AnswerNotFoundException.class, QuestionNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
        var status = HttpStatus.NOT_FOUND.value();
        return getResponse(ex, status);
    }

    @ExceptionHandler({WrongQueryParamException.class, UserExistsWithEmailException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex) {
        var status = HttpStatus.BAD_REQUEST.value();
        return getResponse(ex, status);
    }

    private static ResponseEntity<Object> getResponse(RuntimeException ex, int status) {
        var body = new LinkedHashMap<>();
        body.put(TIMESTAMP, new Date());
        body.put(STATUS, status);
        body.put("error", ex.getMessage());
        return new ResponseEntity<>(body, new HttpHeaders(), status);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               @NonNull HttpHeaders headers,
                                                               HttpStatus status, @NonNull WebRequest request) {

        var body = new LinkedHashMap<>();
        body.put(TIMESTAMP, new Date());
        body.put(STATUS, status.value());

        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }

}

