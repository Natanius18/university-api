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
import software.sigma.internship.entity.Test;
import software.sigma.internship.service.TestService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/tests")
public class TestController {
    private TestService testService;

    @GetMapping
    public List<Test> fetchList(){
        return testService.findAll();
    }

    @GetMapping("/{id}")
    public Test fetch(@PathVariable Long id){
        return testService.findById(id);
    }

    @PostMapping
    public Test save(@RequestBody Test test){
        return testService.save(test);
    }

    @PutMapping
    public Test update(@RequestBody Test test){
        return testService.save(test);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        testService.deleteById(id);
    }
}
