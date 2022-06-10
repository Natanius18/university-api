package software.sigma.internship.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.service.TestService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/tests")
public class TestController {
    private final TestService testService;

    @GetMapping(path = "/all")
    public List<TestDto> fetchList(){
        return testService.findAll();
    }


    @GetMapping
    public List<TestDto> fetchTestsByTeacher(@RequestBody TeacherDto teacher){
        return testService.findTestsByTeacher(teacher);
    }

    @GetMapping("/{id}")
    public TestDto fetch(@PathVariable Long id){
        return testService.findById(id);
    }

    @GetMapping("/{testId}/{qId}")
    public QuestionDto fetch(@PathVariable Long testId, @PathVariable Long qId){
        return testService.findQuestion(testId, qId);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public TestDto save(@RequestBody TestDto test){
        return testService.save(test);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        testService.deleteById(id);
    }
}
