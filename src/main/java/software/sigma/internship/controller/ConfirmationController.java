package software.sigma.internship.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.service.PersonService;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/confirm", produces = "application/json")
public class ConfirmationController {
    private final PersonService personService;


    @GetMapping("/verify")
    public ResponseEntity<Object> verifyUser(@RequestParam String code) {
        int status;
        String message;
        if (personService.verify(code)) {
            status = HttpStatus.OK.value();
            message = "Successfully verified user";
        } else {
            status = HttpStatus.BAD_REQUEST.value();
            message = "Invalid request";
        }
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status);
        body.put("message", message);
        return new ResponseEntity<>(body, new HttpHeaders(), status);
    }
}
