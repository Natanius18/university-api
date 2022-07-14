package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.enums.Role;
import software.sigma.internship.enums.Status;
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.service.TeacherService;
import software.sigma.internship.validator.exception.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper teacherMapper;
    private final BCryptPasswordEncoder encoder;


    @Override
    public List<TeacherDto> findAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers
                .stream()
                .map(teacher -> teacherMapper.map(teacher, TeacherDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDto findById(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return teacherMapper.map(teacher, TeacherDto.class);
    }

    @Override
    public TeacherDto save(@Valid TeacherDto teacherDto) {
        Long id = teacherDto.getId();
        if (id == null || teacherRepository.existsById(id)) {
            teacherDto.setRole(Role.USER);
            teacherDto.setStatus(Status.ACTIVE);
            teacherDto.setPassword(encoder.encode(teacherDto.getPassword()));
            Teacher teacher = teacherRepository.save(teacherMapper.map(teacherDto, Teacher.class));
            return teacherMapper.map(teacher, TeacherDto.class);
        }
        throw new UserNotFoundException(id);
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
}
