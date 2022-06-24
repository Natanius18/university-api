package software.sigma.internship.controller;

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
@RequestMapping(path = "/v1/students")
public class StudentController {
    private final StudentService studentService;
    private ResponseService responseService;

    @GetMapping
    public List<StudentDto> fetchList() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public StudentDto fetch(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @GetMapping("/{id}/responses")
    public List<ResponseDto> fetchResponsesByStudent(@PathVariable Long id){
        return responseService.findByStudent(id);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public StudentDto save(@Valid @RequestBody StudentDto student) {
        return studentService.save(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}
