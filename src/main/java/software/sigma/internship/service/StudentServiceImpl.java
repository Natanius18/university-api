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
        return studentDao.readAll();
    }

    @Override
    public Student read(Long id) {
        return studentDao.read(id);
    }

    @Override
    public Student createStudent(Student student) {
        return studentDao.createStudent(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        return studentDao.updateStudent(id, student);
    }

    @Override
    public void deleteStudent(long id) {
        studentDao.deleteStudent(id);
    }
}
