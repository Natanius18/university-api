package software.sigma.internship.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import software.sigma.internship.service.StudentService;
import software.sigma.internship.user.Student;

import java.util.List;

@RestController
@RequestMapping(path = "/students")
public class RestStudentController {
    private StudentService studentService;

    public RestStudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Student> readAll(){
        return studentService.readAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Student read(@PathVariable Long id){
        return studentService.read(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody Student student){
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Student update(@PathVariable Long id, @RequestBody Student student){
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        studentService.deleteStudent(id);
    }
}
