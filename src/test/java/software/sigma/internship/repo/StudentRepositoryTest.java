package software.sigma.internship.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import software.sigma.internship.UniversityApplication;
import software.sigma.internship.entity.Student;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UniversityApplication.class)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private final Student student = new Student();

    @Test
    public void givenStudentRepository_whenSaveAndRetrieveEntity_thenOK() {
        Student savedStudent = saveNewStudent();

        Student foundEntity = studentRepository.findById(savedStudent.getId()).orElseThrow();

        assertNotNull(foundEntity);
        assertEquals(savedStudent.getId(), foundEntity.getId());
        assertEquals(savedStudent.getCourse(), foundEntity.getCourse());
        assertEquals(savedStudent.getFirstName(), foundEntity.getFirstName());
        assertEquals(savedStudent.getLastName(), foundEntity.getLastName());
        assertEquals(savedStudent.getResponses().size(), foundEntity.getResponses().size());
    }

    @Test
    public void givenStudentRepository_whenDeleteExistingStudentAndRetrieve_thenNotFound() {
        Student savedStudent = saveNewStudent();
        Long id = savedStudent.getId();
        Student foundEntity = studentRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        assertNotNull(foundEntity);

        studentRepository.deleteById(id);
        Optional<Student> optionalStudent = studentRepository.findById(id);
        assertThrows(UserNotFoundException.class,
                () -> optionalStudent.orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Test
    public void givenStudentRepository_whenUpdateExistingStudent_thenOK(){
        Student savedStudent = saveNewStudent();
        savedStudent.setCourse(2);

        Student updatedStudent = studentRepository.save(savedStudent);
        Student foundEntity = studentRepository.findById(savedStudent.getId())
                .orElseThrow(() -> new UserNotFoundException(savedStudent.getId()));

        assertNotNull(foundEntity);
        assertEquals(updatedStudent.getId(), foundEntity.getId());
        assertEquals(updatedStudent.getCourse(), foundEntity.getCourse());
        assertEquals(updatedStudent.getFirstName(), foundEntity.getFirstName());
        assertEquals(updatedStudent.getLastName(), foundEntity.getLastName());
        assertEquals(updatedStudent.getResponses().size(), foundEntity.getResponses().size());

    }

    private Student saveNewStudent() {
        student.setCourse(1);
        student.setFirstName("Natan"); //fixme constant
        student.setLastName("Chachko");
        student.setResponses(new ArrayList<>());

        return studentRepository.save(student);
    }
}
