package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.entity.Test;
import software.sigma.internship.mapper.TestMapper;
import software.sigma.internship.repo.TestRepository;
import software.sigma.internship.service.QuestionService;
import software.sigma.internship.service.TestService;
import software.sigma.internship.validator.exception.TestNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final TestMapper testMapper;
    private final QuestionService questionService;

    @Override
    public List<TestDto> findAll() {
        List<Test> tests = testRepository.findAll();
        return tests.stream()
                .map(entity -> {
                    TestDto testDto = testMapper.toDto(entity);
                    testDto.setQuestions(null);
                    return testDto;
                }).collect(Collectors.toList());
    }

    @Override
    public TestDto findById(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        TestDto testDto = testMapper.toDto(test);
        List<QuestionDto> questions = testDto.getQuestions()
                .stream()
                .map(question -> {
                    question.setAnswers(null);
                    return question;
                })
                .collect(Collectors.toList());
        testDto.setQuestions(questions);
        return testDto;
    }

    @Override
    public QuestionDto findQuestion(Long id, Long qId) {
        if (testRepository.existsById(id)){
            return questionService.findById(qId);
        }
        throw new TestNotFoundException(id);
    }


    @Override
    public TestDto save(TestDto test) {
        Test newTest = testRepository.save(testMapper.toEntity(test));
        return testMapper.toDto(newTest);
    }

    @Override
    public void deleteById(Long id) {
        testRepository.deleteById(id);
    }
}
