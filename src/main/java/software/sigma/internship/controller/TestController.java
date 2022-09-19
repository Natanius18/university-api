package software.sigma.internship.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.service.TestService;

import java.util.List;

@Api(value = "Test controller")
@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/tests", produces = "application/json")
public class TestController {
    private final TestService testService;

    @ApiOperation(value = "Get all existing tests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the tests")
    })
    @GetMapping
    public List<TestDto> fetchList() {
        return testService.findAll();
    }

    @ApiOperation(value = "Get a test by id (for students with hidden field 'correct' and for teachers to let them see the whole test and change it)",
            response = TestDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the test by id"),
            @ApiResponse(code = 404, message = "The test doesn't exist")
    })
    @GetMapping("/{id}")
    public TestDto fetch(@ApiParam(value = "id of the test we want to get")
                                   @PathVariable Long id) {
        return testService.findById(id);
    }

    @ApiOperation(value = "Save or update test")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Test was successfully saved or updated"),
            @ApiResponse(code = 404, message = "The test doesn't exist")
    })
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @PreAuthorize("hasAuthority('writeTest')")
    public TestDto save(@ApiParam(value = "Object of the test to be saved or updated")
                        @RequestBody TestDto test) {
        return testService.save(test);
    }

    @ApiOperation(value = "Delete a test by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('writeTest')")
    public void delete(@ApiParam(value = "id of the test we want to delete")
                       @PathVariable Long id) {
        testService.deleteById(id);
    }
}