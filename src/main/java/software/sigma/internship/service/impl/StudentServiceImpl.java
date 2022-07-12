package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.StudentDto;
import software.sigma.internship.entity.Student;
import software.sigma.internship.enums.Role;
import software.sigma.internship.enums.Status;
import software.sigma.internship.repo.StudentRepository;
import software.sigma.internship.service.StudentService;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper studentMapper;
    private final BCryptPasswordEncoder encoder;

    @Override
    public List<StudentDto> findAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(student -> studentMapper.map(student, StudentDto.class)).collect(Collectors.toList());
    }

    @Override
    public StudentDto findById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return studentMapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto save(StudentDto studentDto) {
        Long id = studentDto.getId();
        if (id == null || studentRepository.existsById(id)) {
            studentDto.setRole(Role.USER);
            studentDto.setStatus(Status.ACTIVE);
            studentDto.setPassword(encoder.encode(studentDto.getPassword()));
            Student student = studentRepository.save(studentMapper.map(studentDto, Student.class));
            return studentMapper.map(student, StudentDto.class);
        }
        throw new UserNotFoundException(id);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
