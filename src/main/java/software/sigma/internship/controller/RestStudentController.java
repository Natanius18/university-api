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
import software.sigma.internship.repo.StudentRepository;
import software.sigma.internship.validator.exception.UserNotFoundException;
import software.sigma.internship.entity.user.Student;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/students")
public class RestStudentController {
    private final StudentRepository studentRepository;

    @GetMapping
    public List<Student> fetchList() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student fetch(@PathVariable Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping
    public Student save(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping
    public Student update(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}
