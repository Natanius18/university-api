package software.sigma.internship.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.mapper.TeacherMapper;
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

    @Spy
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private final Teacher teacher = new Teacher();

    @Before
    public void setup() {
        teacher.setPosition(Teacher.Position.DOCENT);
        teacher.setId(1L);
        teacher.setFirstName("Ivan");
        teacher.setLastName("Ivanov");
    }

    @Test
    public void findByIdShouldTeacherObject() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        TeacherDto savedTeacher = teacherService.findById(teacher.getId());

        assertThat(savedTeacher).isEqualTo(teacherMapper.toDto(teacher));
    }

    @Test(expected = UserNotFoundException.class)
    public void findByIdShouldThrowException(){
        when(teacherRepository.findById(2L)).thenReturn(Optional.empty());
        teacherService.findById(2L);
    }
}
