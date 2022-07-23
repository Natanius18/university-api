package software.sigma.internship.service;

import software.sigma.internship.dto.TeacherDto;

import java.util.List;

/**
 * Basic interface which provides CRUD functionality for teachers.
 * @author natanius
 */
public interface TeacherService {
    /**
     * @return list of all teachers.
     */
    List<TeacherDto> findAll();

    /**
     * @param id id of the teacher we want to get.
     * @return teacher by id.
     */
    TeacherDto findById(Long id);

    /**
     * @param teacher DTO of the teacher we want to save or update.
     * @return DTO of the saved or updated teacher.
     */
    TeacherDto save(TeacherDto teacher);

    /**
     * @param id id of the teacher we want to delete.
     */
    void deleteById(Long id);
}
