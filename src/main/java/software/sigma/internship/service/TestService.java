package software.sigma.internship.service;

import software.sigma.internship.dto.TestDto;

import java.util.List;
/**
 * Basic interface which provides CRUD functionality for tests.
 * @author natanius
 */
public interface TestService {
    /**
     * @return list of all existing tests.
     */
    List<TestDto> findAll();

    /**
     * @param teacherId id of the teacher whose tests we want to get.
     * @return all tests from a specific teacher.
     */
    List<TestDto> findTestsByTeacher(Long teacherId);

    /**
     * @param id id of the test we want to get.
     * @return test by id with hidden field 'correct'.
     */
    TestDto findByIdForStudent(Long id);

    /**
     * @param id id of the test we want to get.
     * @return test by id with all fields shown for teacher.
     */
    TestDto findByIdForTeacher(Long id);

    /**
     * @param id id of the test we want to get.
     * @return test by id.
     */
    TestDto findById(Long id);

    /**
     * @param test test to be saved or updated.
     * @return saved or updated test.
     */
    TestDto save(TestDto test);

    /**
     * Deletes the whole test.
     * @param id id of the test we want to delete.
     */
    void deleteById(Long id);
}
