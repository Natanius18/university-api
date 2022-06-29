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
import software.sigma.internship.dto.ResponseDto;
import software.sigma.internship.dto.StudentDto;
import software.sigma.internship.service.ResponseService;
import software.sigma.internship.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/students", produces = "application/json")
public class StudentController {
    private final StudentService studentService;
    private ResponseService responseService;

    @ApiOperation(value = "Get the list of all students")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the students")
    })
    @GetMapping
    public List<StudentDto> fetchList() {
        return studentService.findAll();
    }

    @ApiOperation(value = "Get a student by id", response = StudentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the student by id"),
            @ApiResponse(code = 404, message = "The student doesn't exist")
    })
    @GetMapping("/{id}")
    public StudentDto fetch(@ApiParam(value = "id of the student we want to get")
                            @PathVariable Long id) {
        return studentService.findById(id);
    }

    @ApiOperation(value = "Get all responses from a specific student")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the responses"),
            @ApiResponse(code = 404, message = "The student doesn't exist")
    })
    @GetMapping("/{id}/responses")
    public List<ResponseDto> fetchResponsesByStudent(@ApiParam(value = "id of the student whose responses we want to get")
                                                     @PathVariable Long id) {
        return responseService.findByStudent(id);
    }

    @ApiOperation(value = "Save or update a student", response = StudentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student was successfully saved or updated"),
            @ApiResponse(code = 404, message = "The student doesn't exist")
    })
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public StudentDto save(@ApiParam(value = "Object of the student to be saved or updated")
                           @Valid @RequestBody StudentDto student) {
        return studentService.save(student);
    }

    @ApiOperation(value = "Delete a student by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted the student by id"),
            @ApiResponse(code = 404, message = "The student doesn't exist")
    })
    @DeleteMapping("/{id}")
    public void delete(@ApiParam(value = "id of the student we want to delete")
                       @PathVariable Long id) {
        studentService.deleteById(id);
    }
}
