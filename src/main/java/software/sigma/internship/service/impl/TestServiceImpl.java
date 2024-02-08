package software.sigma.internship.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.mapper.TestMapper;
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.repo.TestRepository;
import software.sigma.internship.service.TestService;
import software.sigma.internship.validator.exception.TestNotFoundException;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static software.sigma.internship.enums.Permission.READ_FULL;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TeacherRepository teacherRepository;
    private final TestMapper testMapper;

    @Override
    @Cacheable("allTests")
    public List<TestDto> findAll() {
        log.info("Retrieving all tests from database...");
        return testRepository.findAll()
            .stream()
            .map(testMapper::mapWithoutQuestions)
            .toList();
    }

    @Override
    @Cacheable("testsByTeacher")
    public List<TestDto> findTestsByTeacher(Long teacherId) {
        log.info("Retrieving tests of teacher with id " + teacherId + " from database...");
        return teacherRepository.findById(teacherId)
            .map(teacher -> teacher.getTests().stream()
                .map(entity -> {
                    var testDto = testMapper.mapWithoutQuestions(entity);
                    testDto.setTeacher(null);
                    return testDto;
                }).toList())
            .orElseThrow(() -> new UserNotFoundException(teacherId));

    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "tests", key = "#testDto.id", condition = "#testDto.id!=null"),
                    @CacheEvict(value = "testsByTeacher", key = "#testDto.teacher.id"),
                    @CacheEvict(value = "allTests", allEntries = true)
            })
    @Transactional
    public TestDto save(TestDto testDto) {
        var entity = testMapper.map(testDto);
        var teacherId = testDto.getTeacher().getId();
        var teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new UserNotFoundException(teacherId));
        entity.setTeacher(teacher);
        var newTest = testRepository.save(entity);
        return testMapper.mapForTeacher(newTest);
    }

    @Override
    public TestDto findById(Long id) {
        var test = testRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        return userHasPermissionToSeeAllFields() ?
            testMapper.mapForTeacher(test) :
            testMapper.mapForStudent(test);
    }

    private boolean userHasPermissionToSeeAllFields() {
        return getContext()
            .getAuthentication()
            .getAuthorities()
            .contains(READ_FULL.getAuthority());
    }

    @Override
    @CacheEvict(cacheNames = {"tests", "testsByTeacher", "allTests"}, allEntries = true)
    public void deleteById(Long id) {
        testRepository.deleteById(id);
    }

}
