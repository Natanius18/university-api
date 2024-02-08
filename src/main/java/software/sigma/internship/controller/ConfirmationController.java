package software.sigma.internship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.service.PersonService;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/confirm", produces = "application/json")
public class ConfirmationController {

    private final PersonService personService;

    @GetMapping("/verify")
    public ResponseEntity<Object> verifyUser(@RequestParam String code) {
        int status = BAD_REQUEST.value();
        String message = "Invalid request";

        if (personService.verify(code)) {
            status = OK.value();
            message = "Successfully verified user";
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status);
        body.put("message", message);
        return new ResponseEntity<>(body, new HttpHeaders(), status);
    }
}
