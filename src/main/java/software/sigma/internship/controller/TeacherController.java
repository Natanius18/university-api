package software.sigma.internship.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.service.TeacherService;
import software.sigma.internship.service.TestService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/teachers", produces = "application/json")
public class TeacherController {
    private final TeacherService teacherService;
    private final TestService testService;

    @ApiOperation(value = "Save or update a teacher", response = TeacherDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Teacher was successfully saved or updated"),
            @ApiResponse(code = 404, message = "The teacher doesn't exist")
    })
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public TeacherDto save(@ApiParam(value = "Object of the teacher to be saved or updated")
                           @Valid @RequestBody TeacherDto teacher) {
        return teacherService.save(teacher);
    }

    @ApiOperation(value = "Get the list of all teachers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the teachers")
    })
    @GetMapping
    public List<TeacherDto> fetchList() {
        return teacherService.findAll();
    }

    @ApiOperation(value = "Get all tests from a specific teacher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the tests"),
            @ApiResponse(code = 404, message = "The teacher doesn't exist")
    })
    @GetMapping(path = "/{teacherId}/tests")
    public List<TestDto> fetchTestsByTeacher(@ApiParam(value = "id of the teacher whose tests we want to get")
                                             @PathVariable Long teacherId) {
        return testService.findTestsByTeacher(teacherId);
    }

    @ApiOperation(value = "Get a teacher by id", response = TeacherDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the teacher by id"),
            @ApiResponse(code = 404, message = "The teacher doesn't exist")
    })
    @GetMapping("/{id}")
    public TeacherDto fetch(@ApiParam(value = "id of the teacher we want to get")
                            @PathVariable Long id) {
        return teacherService.findById(id);
    }

    @ApiOperation(value = "Delete a teacher by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted the teacher by id"),
            @ApiResponse(code = 404, message = "The teacher doesn't exist")
    })
    @DeleteMapping("/{id}")
    public void delete(@ApiParam(value = "id of the teacher we want to delete")
                       @PathVariable Long id) {
        teacherService.deleteById(id);
    }
}
