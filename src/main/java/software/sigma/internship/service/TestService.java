package software.sigma.internship.service;

import software.sigma.internship.dto.TestDto;

import java.util.List;

public interface TestService {
    List<TestDto> findAll();

    List<TestDto> findTestsByTeacher(Long teacherId);

    TestDto findByIdForStudent(Long id);

    TestDto findByIdForTeacher(Long id);

    TestDto save(TestDto test);

    void deleteById(Long id);
}
