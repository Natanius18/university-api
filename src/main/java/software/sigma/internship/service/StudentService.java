package software.sigma.internship.service;

import software.sigma.internship.dto.StudentDto;

import java.util.List;

/**
 * Basic interface which provides CRUD functionality for students.
 * @author natanius
 */
public interface StudentService {
    /**
     * @return list of all students.
     */
    List<StudentDto> findAll();

    /**
     * @param id id of the student we want to get.
     * @return student by id.
     */
    StudentDto findById(Long id);

    /**
     * @param student DTO of the student we want to save or update.
     * @return DTO of the saved or updated student.
     */
    StudentDto save(StudentDto student);

    /**
     * @param id id of the student we want to delete.
     */
    void deleteById(Long id);
}
