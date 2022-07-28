package software.sigma.internship.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.service.impl.TeacherServiceImpl;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private ModelMapper teacherMapper;

    @Mock
    private PersonService personService;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private final String FIRST_NAME1 = "Ivan";
    private final String LAST_NAME1 = "Ivanov";
    private final String FIRST_NAME2 = "Alex";
    private final String LAST_NAME2 = "Petrov";

    @Test
    public void findByIdShouldTeacherObject() {
        Teacher existingTeacherEntity = createTeacher(1L, Teacher.Position.DOCENT, FIRST_NAME1, LAST_NAME1);
        TeacherDto dtoOfExistingTeacher = createTeacherDto(1L, Teacher.Position.DOCENT, FIRST_NAME1, LAST_NAME1);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(existingTeacherEntity));
        when(teacherMapper.map(existingTeacherEntity, TeacherDto.class)).thenReturn(dtoOfExistingTeacher);

        TeacherDto savedTeacher = teacherService.findById(existingTeacherEntity.getId());

        assertThat(savedTeacher).isEqualTo(teacherMapper.map(existingTeacherEntity, TeacherDto.class));
    }

    @Test(expected = UserNotFoundException.class)
    public void findByIdShouldThrowException(){
        teacherService.findById(2L);
    }

    @Test
    public void saveNewTeacherShouldReturnThatTeacherWithNewId() {
        TeacherDto dtoOfNewTeacherToSave = createTeacherDto(null, Teacher.Position.PROFESSOR, FIRST_NAME2, LAST_NAME2);
        Teacher entityOfNewTeacherToSave = createTeacher(null, Teacher.Position.PROFESSOR, FIRST_NAME2, LAST_NAME2);
        Teacher newSavedTeacherEntity = createTeacher(2L, Teacher.Position.PROFESSOR, FIRST_NAME2, LAST_NAME2);
        TeacherDto newSavedTeacherDto = createTeacherDto(2L, Teacher.Position.PROFESSOR, FIRST_NAME2, LAST_NAME2);

        when(teacherMapper.map(dtoOfNewTeacherToSave, Teacher.class)).thenReturn(entityOfNewTeacherToSave);
        when(teacherRepository.save(entityOfNewTeacherToSave)).thenReturn(newSavedTeacherEntity);
        when(teacherMapper.map(newSavedTeacherEntity, TeacherDto.class)).thenReturn(newSavedTeacherDto);

        TeacherDto returned = teacherService.save(dtoOfNewTeacherToSave);

        assertThat(entityOfNewTeacherToSave.getId()).isNull();
        assertThat(returned.getId()).isEqualTo(2L);
        assertThat(returned).isEqualTo(teacherMapper.map(newSavedTeacherEntity, TeacherDto.class));
    }
    
    @Test
    public void updateExistingTeacherShouldReturnUpdatedTeacher() {
        TeacherDto teacherToUpdateDto = createTeacherDto(2L, Teacher.Position.DOCENT, FIRST_NAME2, LAST_NAME2);
        Teacher entityOfTeacherToUpdate = createTeacher(2L, Teacher.Position.DOCENT, FIRST_NAME2, LAST_NAME2);

        when(teacherRepository.existsById(2L)).thenReturn(true);
        when(teacherMapper.map(teacherToUpdateDto, Teacher.class)).thenReturn(entityOfTeacherToUpdate);
        when(teacherRepository.save(entityOfTeacherToUpdate)).thenReturn(entityOfTeacherToUpdate);
        when(teacherMapper.map(entityOfTeacherToUpdate, TeacherDto.class)).thenReturn(teacherToUpdateDto);

        assertThat(teacherService.save(teacherToUpdateDto)).isEqualTo(teacherToUpdateDto);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateNonExistentTeacherShouldThrowException() {
        TeacherDto teacherToUpdateDto = createTeacherDto(2L, Teacher.Position.DOCENT, FIRST_NAME2, LAST_NAME2);

        when(teacherRepository.existsById(2L)).thenReturn(false);

        assertThat(teacherService.save(teacherToUpdateDto)).isEqualTo(teacherToUpdateDto);
    }

    private Teacher createTeacher(Long id, Teacher.Position position, String firstName, String lastName) {
        Teacher teacher = new Teacher();
        teacher.setPosition(position);
        teacher.setId(id);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        return teacher;
    }

    private TeacherDto createTeacherDto(Long id, Teacher.Position position, String firstName, String lastName) {
        TeacherDto teacher = new TeacherDto();
        teacher.setPosition(position);
        teacher.setId(id);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmail("teacher@gmail.com");
        teacher.setPassword("password");
        return teacher;
    }
}
