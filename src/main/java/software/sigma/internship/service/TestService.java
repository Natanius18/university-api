package software.sigma.internship.service;

import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.dto.TestDto;

import java.util.List;

public interface TestService {
    List<TestDto> findAll();

    TestDto findById(Long id);

    QuestionDto findQuestion(Long id, Long qId);

    TestDto save(TestDto test);

    void deleteById(Long id);
}
