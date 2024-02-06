package software.sigma.internship.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.service.PersonService;
import software.sigma.internship.service.TeacherService;
import software.sigma.internship.validator.exception.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper teacherMapper;
    private final PersonService personService;

    @Override
    public List<TeacherDto> findAll() {
        return teacherRepository.findAll()
                .stream()
                .map(teacher -> teacherMapper.map(teacher, TeacherDto.class))
                .collect(toList());
    }

    @Override
    public TeacherDto findById(Long id) {
        return teacherRepository.findById(id)
            .map(teacher -> teacherMapper.map(teacher, TeacherDto.class))
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public TeacherDto save(@Valid TeacherDto teacherDto) {
        Long id = teacherDto.getId();
        if (id == null || teacherRepository.existsById(id)) {
            personService.preparePersonForSave(teacherDto, id);
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
