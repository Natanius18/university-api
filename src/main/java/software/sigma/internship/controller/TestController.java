package software.sigma.internship.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.service.TestService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Api(value = "Test controller")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/tests", produces = "application/json")
public class TestController {

    private final TestService testService;

    @GetMapping
    public List<TestDto> fetchList() {
        return testService.findAll();
    }

    @GetMapping("/{id}")
    public TestDto fetch(@PathVariable Long id) {
        return testService.findById(id);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @PreAuthorize("hasAuthority('writeTest')")
    @ResponseStatus(CREATED)
    public TestDto save(@RequestBody TestDto test) {
        return testService.save(test);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('writeTest')")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testService.deleteById(id);
    }
}