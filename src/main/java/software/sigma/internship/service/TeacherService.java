package software.sigma.internship.service;

import software.sigma.internship.user.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher save(Teacher teacher);

    List<Teacher> fetchList();

    Teacher update(Teacher teacher, Long id);

    void deleteById(Long id);

    Teacher fetch(Long id);
}
