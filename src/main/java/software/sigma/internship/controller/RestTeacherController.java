package software.sigma.internship.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import software.sigma.internship.service.TeacherService;
import software.sigma.internship.user.Teacher;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/teachers")
public class RestTeacherController {
    private TeacherService teacherService;

    @PostMapping
    public Teacher save(@Valid @RequestBody Teacher teacher){
        return teacherService.save(teacher);
    }

    @GetMapping
    public List<Teacher> fetchList(){
        return teacherService.fetchList();
    }

    @GetMapping("/{id}")
    public Teacher fetch(@PathVariable Long id){
        return teacherService.fetch(id);
    }

    @PutMapping("/{id}")
    public Teacher update(@RequestBody Teacher teacher, @PathVariable("id") Long id){
        return teacherService.update(teacher, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        teacherService.deleteById(id);
    }
}
