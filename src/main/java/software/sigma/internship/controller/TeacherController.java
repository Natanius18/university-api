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
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.service.TeacherService;

import javax.validation.Valid;
import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public Teacher save(@Valid @RequestBody Teacher teacher){
        return teacherService.save(teacher);
    }

    @GetMapping
    public List<Teacher> fetchList(){
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public Teacher fetch(@PathVariable Long id){
        return teacherService.findById(id);
    }


    @PutMapping
    public Teacher update(@RequestBody Teacher teacher){
        return teacherService.save(teacher);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        teacherService.deleteById(id);
    }
}
