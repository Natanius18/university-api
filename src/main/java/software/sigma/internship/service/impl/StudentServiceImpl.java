package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.StudentDto;
import software.sigma.internship.mapper.StudentMapper;
import software.sigma.internship.entity.Student;
import software.sigma.internship.repo.StudentRepository;
import software.sigma.internship.service.StudentService;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<StudentDto> findAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(studentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public StudentDto findById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDto save(StudentDto student) {
        studentRepository.save(studentMapper.toEntity(student));
        return studentMapper.toDto(studentRepository.findById(student.getId())
                .orElseThrow(() -> new UserNotFoundException(student.getId())));
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
