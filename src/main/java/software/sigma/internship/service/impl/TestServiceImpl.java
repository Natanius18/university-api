package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.entity.Test;
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
    private final ModelMapper mapper;
    private final QuestionService questionService;

    @Override
    public List<TestDto> findAll() {
        List<Test> tests = testRepository.findAll();
        return tests.stream()
                .map(entity -> {
                    TestDto testDto = mapper.map(entity, TestDto.class);
                    testDto.setQuestions(null);
                    testDto.getTeacher().setTests(null);
                    return testDto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<TestDto> findTestsByTeacher(TeacherDto teacher) {
        List<Test> tests = testRepository.findTestsByTeacher(mapper.map(teacher, Teacher.class));
        return tests.stream()
                .map(entity -> {
                    TestDto testDto = mapper.map(entity, TestDto.class);
                    testDto.setQuestions(null);
                    testDto.setTeacher(null);
                    return testDto;
                }).collect(Collectors.toList());
    }

    @Override
    public TestDto findById(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        TestDto testDto = mapper.map(test, TestDto.class);
        List<QuestionDto> questions = testDto.getQuestions()
                .stream()
                .map(question -> {
                    question.setAnswers(null);
                    return question;
                })
                .collect(Collectors.toList());
        testDto.setQuestions(questions);
        test.getTeacher().setTests(null);
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
    public TestDto save(TestDto testDto) {
        Long id = testDto.getId();
        if (id == null || testRepository.existsById(id)) {
            Test newTest = testRepository.save(mapper.map(testDto, Test.class));
            return mapper.map(newTest, TestDto.class);
        }
        throw new TestNotFoundException(id);
    }

    @Override
    public void deleteById(Long id) {
        testRepository.deleteById(id);
    }
}
