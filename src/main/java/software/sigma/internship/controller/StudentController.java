package software.sigma.internship.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.entity.Student;
import software.sigma.internship.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<Student> fetchList() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public Student fetch(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @PostMapping
    public Student save(@Valid @RequestBody Student student) {
        return studentService.save(student);
    }

    @PutMapping
    public Student update(@Valid @RequestBody Student student) {
        return studentService.save(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}
