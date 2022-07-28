package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.enums.Role;
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.service.PersonService;
import software.sigma.internship.service.TeacherService;
import software.sigma.internship.validator.exception.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link TeacherService}.
 * @author natanius
 */
@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper teacherMapper;
    private final PersonService personService;

    /**
     * @return list of all teachers.
     */
    @Override
    public List<TeacherDto> findAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers
                .stream()
                .map(teacher -> teacherMapper.map(teacher, TeacherDto.class))
                .collect(Collectors.toList());
    }

    /**
     * @param id id of the teacher we want to get.
     * @return teacher by id.
     */
    @Override
    public TeacherDto findById(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return teacherMapper.map(teacher, TeacherDto.class);
    }

    /**
     * Saves a new teacher, sets {@link Role} USER and activates the account.
     * @param teacherDto DTO of the teacher we want to save or update.
     * @return DTO of the saved or updated teacher.
     */
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

    /**
     * @param id id of the teacher we want to delete.
     */
    @Override
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
}
