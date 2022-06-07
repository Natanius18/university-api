package software.sigma.internship.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.mapper.TeacherMapper;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.validator.exception.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public List<TeacherDto> findAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(teacherMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public TeacherDto findById(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return teacherMapper.toDto(teacher);

    }

    @Override
    public TeacherDto save(@Valid TeacherDto teacher) {
        teacherRepository.save(teacherMapper.toEntity(teacher));
        return teacherMapper.toDto(teacherRepository.findById(teacher.getId())
                .orElseThrow(() -> new UserNotFoundException(teacher.getId())));
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
}
