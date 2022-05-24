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
    public List<Student> readAll() {
        return studentRepository.selectAll();
    }
//TODO
    @Override
    public Student read(Long id) {
        return null;
    }

    @Override
    public Student createStudent(Student student) {
        return null;
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        return null;
    }

    @Override
    public void deleteStudent(long id) {

    }
}
