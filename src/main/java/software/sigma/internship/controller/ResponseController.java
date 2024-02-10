package software.sigma.internship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.ResponseDto;
import software.sigma.internship.service.ResponseService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/responses", produces = "application/json")
public class ResponseController {

    private final ResponseService responseService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('teachers:read')")
    public ResponseDto fetch(@PathVariable Long id) {
        return responseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseDto save(@RequestBody ResponseDto response) {
        return responseService.save(response);
    }

}
