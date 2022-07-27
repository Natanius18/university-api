package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.entity.Answer;
import software.sigma.internship.entity.Question;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.entity.Test;
import software.sigma.internship.enums.Permission;
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.repo.TestRepository;
import software.sigma.internship.service.QuestionService;
import software.sigma.internship.service.TestService;
import software.sigma.internship.validator.exception.TestNotFoundException;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Implementation of {@link TestService}.
 *
 * @author natanius
 */
@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final TeacherRepository teacherRepository;
    private final ModelMapper mapper;
    private final QuestionService questionService;

    /**
     * @return list of all existing tests.
     */
    @Override
    public List<TestDto> findAll() {
        List<Test> tests = testRepository.findAll();
        return tests.stream()
                .map(entity -> mapper.map(entity, TestDto.class))
                .collect(Collectors.toList());
    }

    /**
     * @param teacherId id of the teacher whose tests we want to get.
     * @return all tests from a specific teacher.
     */
    @Override
    public List<TestDto> findTestsByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new UserNotFoundException(teacherId));
        List<Test> tests = teacher.getTests();
        return tests.stream()
                .map(entity -> {
                    TestDto testDto = mapper.map(entity, TestDto.class);
                    testDto.setTeacher(null);
                    return testDto;
                }).collect(Collectors.toList());
    }

    /**
     * @param id id of the test we want to get.
     * @return test by id with hidden field 'correct'.
     */
    public TestDto findByIdForStudent(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        TestDto testDto = mapper.map(test, TestDto.class);
        test.getTeacher().setTests(null);
        List<QuestionDto> questions = getQuestionsWithHiddenCorrectAnswers(test);
        testDto.setQuestions(questions);
        return testDto;
    }

    /**
     * @param id id of the test we want to get.
     * @return test by id with all fields shown for teacher.
     */
    public TestDto findByIdForTeacher(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        TestDto testDto = mapper.map(test, TestDto.class);
        test.getTeacher().setTests(null);
        List<QuestionDto> questions = test.getQuestions()
                .stream()
                .map(question -> mapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());
        testDto.setQuestions(questions);
        return testDto;
    }

    /**
     * @param id id of the test we want to get.
     * @return test by id with all fields shown or hidden field 'correct' depending on authorities.
     */
    @Override
    public TestDto findById(Long id) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities();
        if (authorities.contains(Permission.READ_FULL.getAuthority())) {
            return findByIdForTeacher(id);
        }
        return findByIdForStudent(id);
    }

    /**
     * @param test the test we want to get.
     * @return list of all questions in the test with all options of answers with hidden field 'correct'.
     */
    private List<QuestionDto> getQuestionsWithHiddenCorrectAnswers(Test test) {
        return test.getQuestions()
                .stream()
                .map(question -> {
                    List<AnswerDto> answers = hideCorrectFieldInAnswersOfQuestion(question);
                    QuestionDto questionDto = mapper.map(question, QuestionDto.class);
                    questionDto.setAnswers(answers);
                    return questionDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * @param question question, the correctness of answers to which we want to hide.
     * @return the list of all options of answers with hidden field 'correct'.
     */
    private List<AnswerDto> hideCorrectFieldInAnswersOfQuestion(Question question) {
        return question.getAnswers()
                .stream()
                .map(answer -> {
                    AnswerDto answerDto = mapper.map(answer, AnswerDto.class);
                    answerDto.setCorrect(null);
                    return answerDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * @param testDto test to be saved or updated.
     * @return saved or updated test.
     */
    @Override
    @Transactional
    public TestDto save(TestDto testDto) {
        Long testId = testDto.getId();
        Optional<Test> existingTest = Optional.empty();
        if (testId != null) {
            existingTest = testRepository.findById(testId);
            if (existingTest.isEmpty()) {
                throw new TestNotFoundException(testId);
            }
        }
        Test entity = mapper.map(testDto, Test.class);
        List<Question> questions = mapQuestions(testDto, entity);
        entity.setQuestions(questions);
        deleteQuestionsThatAreNoLongerBelongToTheTest(existingTest, questions);
        Long teacherId = testDto.getTeacher().getId();
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new UserNotFoundException(teacherId));
        entity.setTeacher(teacher);
        Test newTest = testRepository.save(entity);
        return mapper.map(newTest, TestDto.class);
    }

    /**
     * Deletes the whole test.
     * @param id id of the test we want to delete.
     */
    @Override
    public void deleteById(Long id) {
        testRepository.deleteById(id);
    }

    private void deleteQuestionsThatAreNoLongerBelongToTheTest(Optional<Test> existingTest, List<Question> newQuestions) {
        if (existingTest.isPresent()) {
            List<Long> idsOfNewQuestions = getIdsOfQuestions(newQuestions);
            List<Long> idsOfOldQuestions = getIdsOfQuestions(existingTest.get().getQuestions());
            questionService.deleteQuestionByIdIn(getIdsToDelete(idsOfNewQuestions, idsOfOldQuestions));
        }
    }

    /**
     * Parse ids of questions from the given questions.
     * @param questions questions to parse ids.
     * @return list of ids of the given questions.
     */
    private List<Long> getIdsOfQuestions(List<Question> questions) {
        return questions
                .stream()
                .map(Question::getId)
                .collect(Collectors.toList());
    }

    /**
     * Sets correct relations between test, questions and answers.
     * @param testDto DTO of the test.
     * @param entity entity of the test.
     * @return list of mapped questions.
     */
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

    /**
     * Sets correct relations between questions and answers.
     * @param question DTO of the test.
     * @param entityQuestion entity of the test.
     * @return list of mapped answers.
     */
    private List<Answer> mapAnswers(QuestionDto question, Question entityQuestion) {
        return question.getAnswers().stream()
                .map(answer -> {
                    Answer entityAnswer = mapper.map(answer, Answer.class);
                    entityAnswer.setQuestion(entityQuestion);
                    return entityAnswer;
                }).collect(Collectors.toList());
    }

    /**
     *
     * @param idsOfNewQuestions ids of the questions in updated test.
     * @param idsOfOldQuestions ids of the questions in old test.
     * @return list of ids of questions that are no longer present in the updated test.
     */
    public List<Long> getIdsToDelete(List<Long> idsOfNewQuestions, List<Long> idsOfOldQuestions) {
        List<Long> result = new ArrayList<>(idsOfNewQuestions);
        result.addAll(idsOfOldQuestions);

        List<Long> intersection = new ArrayList<>(idsOfNewQuestions);
        intersection.retainAll(idsOfOldQuestions);

        result.removeAll(intersection);
        return result;
    }
}
