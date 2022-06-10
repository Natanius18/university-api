package software.sigma.internship.service;

import org.junit.Before;
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

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private final Teacher teacher = new Teacher();
    private final TeacherDto teacher1Dto = new TeacherDto();
    private final Teacher newSavedTeacher = new Teacher();
    private final TeacherDto teacherDto = new TeacherDto();
    private final TeacherDto teacherEntityToDto = new TeacherDto();
    private final Teacher teacherDtoToEntity = new Teacher();

    @Before
    public void setup() {
        teacher.setPosition(Teacher.Position.DOCENT);
        teacher.setId(1L);
        teacher.setFirstName("Ivan");
        teacher.setLastName("Ivanov");

        teacher1Dto.setPosition(Teacher.Position.DOCENT);
        teacher1Dto.setId(1L);
        teacher1Dto.setFirstName("Ivan");
        teacher1Dto.setLastName("Ivanov");

        teacherDto.setPosition(Teacher.Position.PROFESSOR);
        teacherDto.setFirstName("Alex");
        teacherDto.setLastName("Petrov");

        teacherDtoToEntity.setPosition(Teacher.Position.PROFESSOR);
        teacherDtoToEntity.setFirstName("Alex");
        teacherDtoToEntity.setLastName("Petrov");

        newSavedTeacher.setPosition(Teacher.Position.PROFESSOR);
        newSavedTeacher.setId(2L);
        newSavedTeacher.setFirstName("Alex");
        newSavedTeacher.setLastName("Petrov");

        teacherEntityToDto.setPosition(Teacher.Position.PROFESSOR);
        teacherEntityToDto.setId(2L);
        teacherEntityToDto.setFirstName("Alex");
        teacherEntityToDto.setLastName("Petrov");

    }

    @Test
    public void findByIdShouldTeacherObject() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherMapper.map(teacher, TeacherDto.class)).thenReturn(teacher1Dto);

        TeacherDto savedTeacher = teacherService.findById(teacher.getId());

        assertThat(savedTeacher).isEqualTo(teacherMapper.map(teacher, TeacherDto.class));
    }

    @Test(expected = UserNotFoundException.class)
    public void findByIdShouldThrowException(){
        teacherService.findById(2L);
    }

    @Test
    public void saveNewTeacherShouldReturnThatTeacherWithNewId(){
        when(teacherMapper.map(teacherDto, Teacher.class)).thenReturn(teacherDtoToEntity);
        when(teacherRepository.save(teacherDtoToEntity)).thenReturn(newSavedTeacher);
        when(teacherMapper.map(newSavedTeacher, TeacherDto.class)).thenReturn(teacherEntityToDto);

        TeacherDto returned = teacherService.save(teacherDto);

        assertThat(teacherDtoToEntity.getId()).isNull();
        assertThat(returned.getId()).isEqualTo(2L);
        assertThat(returned).isEqualTo(teacherMapper.map(newSavedTeacher, TeacherDto.class));
    }

//    @Test
//    public void updateExistingTeacherShouldReturnUpdatedTeacher(){
//        //TODO
//    }
}
