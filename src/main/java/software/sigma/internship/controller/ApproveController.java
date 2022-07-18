package software.sigma.internship.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.PersonDto;
import software.sigma.internship.service.PersonService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/admin-panel", produces = "application/json")
public class ApproveController {
    private final PersonService personService;

    @ApiOperation(value = "Give a role to a user", response = PersonDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The role was updated successfully"),
            @ApiResponse(code = 404, message = "The person doesn't exist")
    })
    @PutMapping
    @PreAuthorize("hasAuthority('admin:approveProfile')")
    public PersonDto approve(@ApiParam(value = "Object of the student to be saved or updated")
                           @Valid @RequestBody PersonDto person) {
        return personService.approve(person.getEmail(), person.getRole());
    }
}

