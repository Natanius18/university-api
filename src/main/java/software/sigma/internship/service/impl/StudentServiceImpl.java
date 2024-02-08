package software.sigma.internship.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.StudentDto;
import software.sigma.internship.mapper.StudentMapper;
import software.sigma.internship.repo.StudentRepository;
import software.sigma.internship.service.PersonService;
import software.sigma.internship.service.StudentService;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PersonService personService;


    @Override
    public List<StudentDto> findAll() {
        return studentRepository.findAll()
            .stream()
            .map(studentMapper::map)
            .toList();
    }

    @Override
    public StudentDto findById(Long id) {
        return studentRepository.findById(id)
            .map(studentMapper::map)
            .orElseThrow(() -> new UserNotFoundException(id));

    }

    @Override
    public StudentDto save(StudentDto studentDto) {
        var id = studentDto.getId();
        if (id == null || studentRepository.existsById(id)) {
            personService.preparePersonForSave(studentDto, id);
            var student = studentRepository.save(studentMapper.map(studentDto));
            return studentMapper.map(student);
        }
        throw new UserNotFoundException(id);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
