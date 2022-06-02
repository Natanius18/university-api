package software.sigma.internship.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.entity.Student;
import software.sigma.internship.repo.StudentRepository;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

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
