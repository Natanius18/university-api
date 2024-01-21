package software.sigma.internship.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import software.sigma.internship.dto.StudentDto;
import software.sigma.internship.entity.Student;
import software.sigma.internship.enums.Role;
import software.sigma.internship.enums.Status;
import software.sigma.internship.repo.StudentRepository;
import software.sigma.internship.service.impl.StudentServiceImpl;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "Password";

    @Mock
    private StudentRepository studentRepository;

    @Spy
    private ModelMapper studentMapper;

    @Mock
    private PersonService personService;

    @InjectMocks
    private StudentServiceImpl studentService;

    @ParameterizedTest(name = "Return student with id {0}")
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void findById_shouldReturnStudentWithId(Long id) {
        Student student = createStudent(id);
        StudentDto studentDto = createStudentDto(id);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        StudentDto found = studentService.findById(id);

        assertEquals(studentDto, found);
    }

    @Test
    void findById_whenStudentIsNotFound_shouldThrowException() {
        when(studentRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> studentService.findById(5L));
    }

    @Test
    void save_shouldReturnStudentWithNewId() {
        Student student = createStudent(null);
        Student savedStudent = createStudent(1L);
        StudentDto studentDto = createStudentDto(null);
        StudentDto savedStudentDto = createStudentDto(1L);
        when(studentRepository.save(student)).thenReturn(savedStudent);
        when(studentMapper.map(studentDto, Student.class)).thenReturn(student);

        StudentDto newStudent = studentService.save(studentDto);

        assertEquals(savedStudentDto, newStudent);
    }

    @Test
    void save_shouldUpdateExistingStudent() {
        Student updatedStudent = createStudent(1L);
        StudentDto updatedStudentDto = createStudentDto(1L);
        when(studentRepository.save(updatedStudent)).thenReturn(updatedStudent);
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentMapper.map(updatedStudentDto, Student.class)).thenReturn(updatedStudent);

        StudentDto returnedStudent = studentService.save(updatedStudentDto);

        assertEquals(updatedStudentDto, returnedStudent);
    }

    @Test
    void save_whenTryToUpdateNotExistingStudent_shouldThrowException() {
        StudentDto updatedStudentDto = createStudentDto(1L);
        when(studentRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> studentService.save(updatedStudentDto));
    }

    @ParameterizedTest
    @ArgumentsSource(AllStudentsArgumentsProvider.class)
    void findAll_shouldReturnListOfAllStudents(List<StudentDto> expectedResult, List<Student> students) {
        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDto> allStudents = studentService.findAll();

        assertEquals(expectedResult, allStudents);
    }

    @Test
    void deleteById_shouldInvokeDeleteMethodInRepository() {
        studentService.deleteById(any());

        verify(studentRepository).deleteById(any());
    }


    private static Student createStudent(Long id) {
        Student student = new Student();
        student.setCourse(1);
        student.setId(id);
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL);
        student.setPassword(PASSWORD);
        student.setStatus(Status.ACTIVE);
        student.setRole(Role.STUDENT);
        return student;
    }

    private static StudentDto createStudentDto(Long id) {
        StudentDto student = new StudentDto();
        student.setId(id);
        student.setCourse(1);
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL);
        student.setPassword(PASSWORD);
        student.setStatus(Status.ACTIVE);
        student.setRole(Role.STUDENT);
        return student;
    }


    static class AllStudentsArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            Student student1 = createStudent(1L);
            Student student2 = createStudent(2L);
            Student student3 = createStudent(3L);

            StudentDto studentDto1 = createStudentDto(1L);
            StudentDto studentDto2 = createStudentDto(2L);
            StudentDto studentDto3 = createStudentDto(3L);
            return Stream.of(
                    Arguments.of(List.of(studentDto1, studentDto2, studentDto3), List.of(student1, student2, student3)),
                    Arguments.of(List.of(studentDto1, studentDto2), List.of(student1, student2)),
                    Arguments.of(List.of(studentDto1), List.of(student1)),
                    Arguments.of(List.of(), List.of())
            );
        }
    }
}



