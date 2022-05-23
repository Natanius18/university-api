package software.sigma.internship.service.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import software.sigma.internship.entity.Student;
import software.sigma.internship.repo.StudentRepository;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.List;

@Repository
@AllArgsConstructor
public class StudentDaoImpl implements StudentDao {
    private final StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
