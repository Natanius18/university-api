package software.sigma.internship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.PersonDto;
import software.sigma.internship.service.PersonService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/admin-panel", produces = "application/json")
public class ApproveController {
    private final PersonService personService;

    @PutMapping
    @PreAuthorize("hasAuthority('admin:approveProfile')")
    public PersonDto approve(@Valid @RequestBody PersonDto person) {
        return personService.approve(person.getEmail(), person.getRole());
    }
}

