package software.sigma.internship.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.service.dao.StudentDao;
import software.sigma.internship.entity.Student;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentDao.findById(id);
    }

    @Override
    public Student save(Student student) {
        return studentDao.save(student);
    }

    @Override
    public void deleteById(Long id) {
        studentDao.deleteById(id);
    }
}
