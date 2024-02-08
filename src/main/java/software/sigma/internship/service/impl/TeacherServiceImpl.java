package software.sigma.internship.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.mapper.TeacherMapper;
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.service.PersonService;
import software.sigma.internship.service.TeacherService;
import software.sigma.internship.validator.exception.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final PersonService personService;

    @Override
    public List<TeacherDto> findAll() {
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::map)
                .toList();
    }

    @Override
    public TeacherDto findById(Long id) {
        return teacherRepository.findById(id)
            .map(teacherMapper::map)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public TeacherDto save(@Valid TeacherDto teacherDto) {
        Long id = teacherDto.getId();
        if (id == null || teacherRepository.existsById(id)) {
            personService.preparePersonForSave(teacherDto, id);
            Teacher teacher = teacherRepository.save(teacherMapper.map(teacherDto));
            return teacherMapper.map(teacher);
        }
        throw new UserNotFoundException(id);
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
}
