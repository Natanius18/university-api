package software.sigma.internship.dao;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.sigma.internship.dao.repo.StudentRepository;
import software.sigma.internship.user.Student;

import java.util.List;

@Component
public class StudentDaoImpl implements StudentDao {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentDaoImpl(Jdbi jdbi) {
        this.studentRepository = jdbi.onDemand(StudentRepository.class);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.selectAll();
    }

    @Override
    public Student read(Long id) {
        return studentRepository.selectById(id);
    }

    @Override
    public Student create(Student student) {
        studentRepository.insert(student);
        return studentRepository.findByName(student.getFirstName(), student.getLastName());
    }

    @Override
    public Student update(Long id, Student student) {
        studentRepository.update(id, student);
        return studentRepository.selectById(id);
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(id);
    }
}
