package software.sigma.internship.dao;

import org.springframework.stereotype.Repository;
import software.sigma.internship.user.Teacher;

import java.util.List;

@Repository
public class TeacherDaoImpl implements TeacherDao {
    @Override
    public List<Teacher> readAll() {
        return null;
    }

    @Override
    public Teacher read(Long id) {
        return null;
    }

    @Override
    public Teacher createTeacher(Teacher student) {
        return null;
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher student) {
        return null;
    }

    @Override
    public void deleteTeacher(long id) {

    }
}
