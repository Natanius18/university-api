package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.StudentDto;
import software.sigma.internship.entity.Student;
import software.sigma.internship.enums.Role;
import software.sigma.internship.repo.StudentRepository;
import software.sigma.internship.service.PersonService;
import software.sigma.internship.service.StudentService;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link StudentService}.
 * @author natanius
 */
@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper studentMapper;
    private final PersonService personService;

    /**
     * @return list of all students.
     */
    @Override
    public List<StudentDto> findAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(student -> studentMapper.map(student, StudentDto.class)).collect(Collectors.toList());
    }

    /**
     * @param id id of the student we want to get.
     * @return student by id.
     */
    @Override
    public StudentDto findById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return studentMapper.map(student, StudentDto.class);
    }

    /**
     * Saves a new student, sets {@link Role} USER and activates the account.
     * @param studentDto DTO of the student we want to save or update.
     * @return DTO of the saved or updated student.
     */
    @Override
    public StudentDto save(StudentDto studentDto) {
        Long id = studentDto.getId();
        if (id == null || studentRepository.existsById(id)) {
            personService.preparePersonForSave(studentDto, id);
            Student student = studentRepository.save(studentMapper.map(studentDto, Student.class));
            return studentMapper.map(student, StudentDto.class);
        }
        throw new UserNotFoundException(id);
    }

    /**
     * @param id id of the student we want to delete.
     */
    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
