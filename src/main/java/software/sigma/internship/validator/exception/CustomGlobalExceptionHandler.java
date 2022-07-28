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
import software.sigma.internship.mongo.filters.exceptions.WrongQueryParam;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, TestNotFoundException.class,
            AnswerNotFoundException.class, QuestionNotFoundException.class,
            WrongQueryParam.class, UserExistsWithEmailException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        int status =
                (ex instanceof WrongQueryParam || ex instanceof UserExistsWithEmailException) ?
                        HttpStatus.BAD_REQUEST.value() : HttpStatus.NOT_FOUND.value();
        body.put("timestamp", new Date());
        body.put("status", status);
        body.put("error", ex.getMessage());
        return new ResponseEntity<>(body, new HttpHeaders(), status);
    }


    @Override
    @NonNull
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               @NonNull HttpHeaders headers,
                                                               HttpStatus status, @NonNull WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }

}

