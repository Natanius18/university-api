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
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.validator.exception.UserNotFoundException;
import software.sigma.internship.entity.user.Teacher;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/teachers")
public class RestTeacherController {
    private TeacherRepository teacherRepository;

    @PostMapping
    public Teacher save(@Valid @RequestBody Teacher teacher){
        return teacherRepository.save(teacher);
    }

    @GetMapping
    public List<Teacher> fetchList(){
        return teacherRepository.findAll();
    }

    @GetMapping("/{id}")
    public Teacher fetch(@PathVariable Long id){
        return teacherRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }


    @PutMapping
    public Teacher update(@RequestBody Teacher teacher){
        return teacherRepository.save(teacher);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        teacherRepository.deleteById(id);
    }
}
