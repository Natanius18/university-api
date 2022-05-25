package software.sigma.internship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.sigma.internship.dao.StudentDao;
import software.sigma.internship.user.Student;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public List<Student> readAll() {
        return studentDao.findAll();
    }

    @Override
    public Student read(Long id) {
        return studentDao.read(id);
    }

    @Override
    public Student create(Student student) {
        return studentDao.create(student);
    }

    @Override
    public Student update(Long id, Student student) {
        return studentDao.update(id, student);
    }

    @Override
    public void delete(Long id) {
        studentDao.delete(id);
    }
}
