package software.sigma.internship.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.dto.TestMapper;
import software.sigma.internship.entity.Test;
import software.sigma.internship.repo.TestRepository;
import software.sigma.internship.validator.exception.TestNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private TestRepository testRepository;
    private final TestMapper testMapper;

    @Override
    public List<TestDto> findAll() {
        List<Test> tests = testRepository.findAll();
        return tests.stream().map(entity -> {
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
                .peek(question -> question.setAnswers(null))
                .collect(Collectors.toList());
        testDto.setQuestions(questions);
        return testDto;
    }

    @Override
    public QuestionDto findQuestion(Long id, Long qId) {
        Test test = testRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        TestDto testDto = testMapper.toDto(test);
        return testDto.getQuestions()
                .stream()
                .filter(questionDto -> questionDto.getId().equals(qId))
                .findFirst()
                .orElseThrow(() -> new TestNotFoundException(qId));
    }

    @Override
    public TestDto save(TestDto test) {
        testRepository.save(testMapper.toEntity(test));
        return testMapper.toDto(testRepository.findById(test.getId())
                .orElseThrow(() -> new TestNotFoundException(test.getId())));
    }

    @Override
    public void deleteById(Long id) {
        testRepository.deleteById(id);
    }
}
