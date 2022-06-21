package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.entity.Answer;
import software.sigma.internship.entity.Question;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.entity.Test;
import software.sigma.internship.repo.TestRepository;
import software.sigma.internship.service.QuestionService;
import software.sigma.internship.service.TestService;
import software.sigma.internship.validator.exception.TestNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        test.getTeacher().setTests(null);
        return testDto;
    }

    @Override
    public QuestionDto findQuestion(Long id, Long qId) {
        if (testRepository.existsById(id)) {
            return questionService.findById(qId);
        }
        throw new TestNotFoundException(id);
    }


    @Override
    @Transactional
    public TestDto save(TestDto testDto) {
        Long testId = testDto.getId();
        if (testId != null && testRepository.findById(testId).isEmpty()) {
            throw new TestNotFoundException(testId);
        } else {
            Test entity = mapper.map(testDto, Test.class);
            List<Question> questions = mapQuestions(testDto, entity);
            entity.setQuestions(questions);
            deleteQuestionsThatAreNoLongerBelongToTheTest(testId, questions);
            Test newTest = testRepository.save(entity);
            return mapper.map(newTest, TestDto.class);
        }
    }

    @Override
    public void deleteById(Long id) {
        testRepository.deleteById(id);
    }

    private void deleteQuestionsThatAreNoLongerBelongToTheTest(Long testId, List<Question> newQuestions) {
        if (testId != null) {
            Optional<Test> existingTest = testRepository.findById(testId);
            if (existingTest.isPresent()) {
                List<Long> idsOfNewQuestions = getIdsOfQuestions(newQuestions);
                List<Long> idsOfOldQuestions = getIdsOfQuestions(existingTest.get().getQuestions());
                questionService.deleteQuestionByIdIn(getIdsToDelete(idsOfNewQuestions, idsOfOldQuestions));
            }
        }
    }

    private List<Long> getIdsOfQuestions(List<Question> questions) {
        return questions
                .stream()
                .map(Question::getId)
                .collect(Collectors.toList());
    }


    private List<Question> mapQuestions(TestDto testDto, Test entity) {
        return testDto.getQuestions()
                .stream()
                .map(question -> {
                    Question entityQuestion = mapper.map(question, Question.class);
                    entityQuestion.setTest(entity);
                    List<Answer> answers = mapAnswers(question, entityQuestion);
                    entityQuestion.setAnswers(answers);
                    return entityQuestion;
                }).collect(Collectors.toList());
    }

    private List<Answer> mapAnswers(QuestionDto question, Question entityQuestion) {
        return question.getAnswers().stream()
                .map(answer -> {
                    Answer entityAnswer = mapper.map(answer, Answer.class);
                    entityAnswer.setQuestion(entityQuestion);
                    return entityAnswer;
                }).collect(Collectors.toList());
    }


    public List<Long> getIdsToDelete(List<Long> idsOfNewQuestions, List<Long> idsOfOldQuestions) {
        List<Long> result = new ArrayList<>(idsOfNewQuestions);
        result.addAll(idsOfOldQuestions);

        List<Long> intersection = new ArrayList<>(idsOfNewQuestions);
        intersection.retainAll(idsOfOldQuestions);

        result.removeAll(intersection);
        return result;
    }
}
