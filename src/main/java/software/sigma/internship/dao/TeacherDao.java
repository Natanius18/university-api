package software.sigma.internship.dao;

import software.sigma.internship.user.Teacher;
import java.util.List;

public interface TeacherDao {
    List<Teacher> readAll();

    Teacher read(Long id);

    Teacher createTeacher(Teacher student);

    Teacher updateTeacher(Long id, Teacher student);

    void deleteTeacher(long id);
}
