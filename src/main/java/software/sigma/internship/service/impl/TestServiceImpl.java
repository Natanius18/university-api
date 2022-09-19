package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.entity.Answer;
import software.sigma.internship.entity.Question;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.entity.Test;
import software.sigma.internship.enums.Permission;
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.repo.TestRepository;
import software.sigma.internship.service.TestService;
import software.sigma.internship.validator.exception.TestNotFoundException;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.Collection;
import java.util.List;
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
    private final ModelMapper allTestsMapper;
    private final ModelMapper testForTeacherMapper;
    private final ModelMapper testForStudentMapper;

    /**
     * @return list of all existing tests.
     */
    @Override
    public List<TestDto> findAll() {
        List<Test> tests = testRepository.findAll();
        return tests.stream()
                .map(entity -> allTestsMapper.map(entity, TestDto.class))
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
                    TestDto testDto = allTestsMapper.map(entity, TestDto.class);
                    testDto.setTeacher(null);
                    return testDto;
                }).collect(Collectors.toList());
    }

    /**
     * @param id id of the test we want to get.
     * @return test by id with all fields shown or hidden field 'correct' depending on authorities.
     */
    @Override
    public TestDto findById(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        return userHasPermissionToSeeAllFields() ?
                testForTeacherMapper.map(test, TestDto.class) :
                testForStudentMapper.map(test, TestDto.class);
    }

    private boolean userHasPermissionToSeeAllFields() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities();
        return authorities.contains(Permission.READ_FULL.getAuthority());
    }

    /**
     * @param testDto test to be saved or updated.
     * @return saved or updated test.
     */
    @Override
    @Transactional
    public TestDto save(TestDto testDto) {
        Test entity = testForTeacherMapper.map(testDto, Test.class);
        List<Question> questions = mapQuestions(testDto, entity);
        entity.setQuestions(questions);
        Long teacherId = testDto.getTeacher().getId();
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new UserNotFoundException(teacherId));
        entity.setTeacher(teacher);
        Test newTest = testRepository.save(entity);
        return testForTeacherMapper.map(newTest, TestDto.class);
    }

    /**
     * Deletes the whole test.
     * @param id id of the test we want to delete.
     */
    @Override
    public void deleteById(Long id) {
        testRepository.deleteById(id);
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
                    Question entityQuestion = testForTeacherMapper.map(question, Question.class);
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
                    Answer entityAnswer = testForTeacherMapper.map(answer, Answer.class);
                    entityAnswer.setQuestion(entityQuestion);
                    return entityAnswer;
                }).collect(Collectors.toList());
    }

}
