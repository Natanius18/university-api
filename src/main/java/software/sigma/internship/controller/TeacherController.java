package software.sigma.internship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.service.TeacherService;
import software.sigma.internship.service.TestService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/teachers", produces = "application/json")
public class TeacherController {

    private final TeacherService teacherService;
    private final TestService testService;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseStatus(CREATED)
    public TeacherDto save(@Valid @RequestBody TeacherDto teacher) {
        return teacherService.save(teacher);
    }

    @GetMapping
    public List<TeacherDto> fetchList() {
        return teacherService.findAll();
    }

    @GetMapping(path = "/{teacherId}/tests")
    public List<TestDto> fetchTestsByTeacher(@PathVariable Long teacherId) {
        return testService.findTestsByTeacher(teacherId);
    }

    @GetMapping("/{id}")
    public TeacherDto fetch(@PathVariable Long id) {
        return teacherService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        teacherService.deleteById(id);
    }
}
