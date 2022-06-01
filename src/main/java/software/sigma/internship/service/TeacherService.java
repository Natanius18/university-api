package software.sigma.internship.service;

import software.sigma.internship.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();

    Teacher findById(Long id);

    Teacher save(Teacher teacher);

    void deleteById(Long id);
}
