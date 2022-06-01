package software.sigma.internship.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.service.dao.TeacherDao;
import software.sigma.internship.entity.Teacher;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private TeacherDao teacherDao;

    @Override
    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    @Override
    public Teacher findById(Long id) {
        return teacherDao.findById(id);
    }

    @Override
    public Teacher save(Teacher teacher) {
        return teacherDao.save(teacher);
    }

    @Override
    public void deleteById(Long id) {
        teacherDao.deleteById(id);
    }
}
