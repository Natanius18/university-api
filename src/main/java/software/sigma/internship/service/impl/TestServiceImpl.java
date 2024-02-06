package software.sigma.internship.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final TeacherRepository teacherRepository;
    private final ModelMapper allTestsMapper;
    private final ModelMapper testForTeacherMapper;
    private final ModelMapper testForStudentMapper;
    
    @Override
    @Cacheable("allTests")
    public List<TestDto> findAll() {
        log.info("Retrieving all tests from database...");
        List<Test> tests = testRepository.findAll();
        return tests.stream()
                .map(entity -> allTestsMapper.map(entity, TestDto.class))
                .collect(toList());
    }

    @Override
    @Cacheable("testsByTeacher")
    public List<TestDto> findTestsByTeacher(Long teacherId) {
        log.info("Retrieving tests of teacher with id " + teacherId + " from database...");
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new UserNotFoundException(teacherId));
        List<Test> tests = teacher.getTests();
        return tests.stream()
                .map(entity -> {
                    TestDto testDto = allTestsMapper.map(entity, TestDto.class);
                    testDto.setTeacher(null);
                    return testDto;
                }).collect(toList());
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "tests", key = "#testDto.id", condition = "#testDto.id!=null"),
                    @CacheEvict(value = "testsByTeacher", key = "#testDto.teacher.id"),
                    @CacheEvict(value = "allTests", allEntries = true)
            })
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

    @Override
    @CacheEvict(cacheNames = {"tests", "testsByTeacher", "allTests"}, allEntries = true)
    public void deleteById(Long id) {
        testRepository.deleteById(id);
    }

    private List<Question> mapQuestions(TestDto testDto, Test entity) {
        return testDto.getQuestions()
                .stream()
                .map(question -> {
                    Question entityQuestion = testForTeacherMapper.map(question, Question.class);
                    entityQuestion.setTest(entity);
                    List<Answer> answers = mapAnswers(question, entityQuestion);
                    entityQuestion.setAnswers(answers);
                    return entityQuestion;
                }).collect(toList());
    }

    
    private List<Answer> mapAnswers(QuestionDto question, Question entityQuestion) {
        return question.getAnswers().stream()
                .map(answer -> {
                    Answer entityAnswer = testForTeacherMapper.map(answer, Answer.class);
                    entityAnswer.setQuestion(entityQuestion);
                    return entityAnswer;
                }).collect(toList());
    }
}
