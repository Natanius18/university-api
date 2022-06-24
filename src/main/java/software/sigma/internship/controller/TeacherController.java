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
@RequestMapping(path = "/v1/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final TestService testService;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public TeacherDto save(@Valid @RequestBody TeacherDto teacher){
        return teacherService.save(teacher);
    }

    @GetMapping
    public List<TeacherDto> fetchList(){
        return teacherService.findAll();
    }

    @ApiOperation(value = "Get all tests from a specific teacher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the tests")
    })
    @GetMapping(path = "/{teacherId}/tests")
    public List<TestDto> fetchTestsByTeacher(
            @ApiParam(value = "id of the teacher whose tests we want to get")
            @PathVariable Long teacherId) {
        return testService.findTestsByTeacher(teacherId);
    }

    @GetMapping("/{id}")
    public TeacherDto fetch(@PathVariable Long id){
        return teacherService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        teacherService.deleteById(id);
    }
}
