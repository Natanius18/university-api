package cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import software.sigma.internship.dto.StudentDto;
import software.sigma.internship.entity.Student;
import software.sigma.internship.enums.Role;
import software.sigma.internship.enums.Status;
import software.sigma.internship.repo.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class StudentSteps {
    private static final String URL = "http://localhost:8080/university/v1/students";
    private StudentDto createdStudentDto = new StudentDto();
    private Student existingStudent;
    private StudentDto foundStudent;

    private static final String FIRST_NAME = "First";
    private static final String LAST_NAME = "Last";
    private static final String EMAIL = "email@example.com";
    private static final String PASSWORD = "Password";
    private final RestTemplate restTemplate = new RestTemplate();
    private final StudentRepository studentRepository;
    private Long idOfNotExistingStudent;
    private HttpClientErrorException expectedException;

    public StudentSteps(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @When("new student is added")
    public void newStudentIsRegistered() throws IOException {
        String requestJson = new String(Files.readAllBytes(Paths.get("src/test/resources/student.json")));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        createdStudentDto = restTemplate.postForObject(URL, entity, StudentDto.class);
    }

    @Then("the student is present in the database")
    public void newAccountIsCreatedAndSavedInTheDatabase() {
        Student foundStudent = studentRepository.findById(createdStudentDto.getId()).get();
        assertEquals(createdStudentDto.getId(), foundStudent.getId());
        assertEquals(createdStudentDto.getFirstName(), foundStudent.getFirstName());
        assertEquals(createdStudentDto.getLastName(), foundStudent.getLastName());
        assertEquals(createdStudentDto.getEmail(), foundStudent.getEmail());
        assertEquals(createdStudentDto.getId(), foundStudent.getId());
        assertEquals(createdStudentDto.getCourse(), foundStudent.getCourse());
        studentRepository.deleteById(createdStudentDto.getId());
    }

    @Given("there is a student in the database")
    public void thereIsAStudentInTheDatabase() {
        existingStudent = studentRepository.save(createStudent());
        assertTrue(studentRepository.existsById(existingStudent.getId()));
    }

    @When("trying to retrieve an existing student")
    public void tryingToRetrieveAnExistingStudent() {
        foundStudent = restTemplate.getForEntity(URL + "/" + existingStudent.getId(), StudentDto.class).getBody();
    }

    @Then("getting an existing student")
    public void gettingAnExistingStudent() {
        assertEquals(existingStudent.getId(), foundStudent.getId());
        assertEquals(existingStudent.getCourse(), foundStudent.getCourse());
        assertEquals(existingStudent.getEmail(), foundStudent.getEmail());
        assertEquals(existingStudent.getFirstName(), foundStudent.getFirstName());
        assertEquals(existingStudent.getLastName(), foundStudent.getLastName());

        studentRepository.deleteById(existingStudent.getId());
    }

    @When("existing student is updated")

    public void existingStudentIsUpdated() throws JsonProcessingException {
        existingStudent.setCourse(3);
        existingStudent.setLastName("Changed");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(existingStudent);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        createdStudentDto = restTemplate.postForObject(URL, entity, StudentDto.class);
    }

    @Then("updated student is saved in the database")
    public void updatedStudentIsSavedInTheDatabase() {
        Student updatedStudent = studentRepository.findById(existingStudent.getId()).get();
        assertEquals(existingStudent.getId(), updatedStudent.getId());
        assertEquals(existingStudent.getCourse(), updatedStudent.getCourse());
        assertEquals(existingStudent.getEmail(), updatedStudent.getEmail());
        assertEquals(existingStudent.getFirstName(), updatedStudent.getFirstName());
        assertEquals(existingStudent.getLastName(), updatedStudent.getLastName());

        studentRepository.deleteById(existingStudent.getId());
    }

    @Given("there is no student in the database with given id")
    public void thereIsNoStudentInTheDatabaseWithGivenId() {
        idOfNotExistingStudent = studentRepository.save(createStudent()).getId();
        studentRepository.deleteById(idOfNotExistingStudent);
        assertFalse(studentRepository.existsById(idOfNotExistingStudent));
    }

    @When("not existing student is updated")
    public void notExistingStudentIsUpdated() {
        StudentDto studentDto = createStudentDto();
        studentDto.setId(idOfNotExistingStudent);

        HttpEntity<StudentDto> updatedStudent = new HttpEntity<>(studentDto);

        try {
            restTemplate.exchange(URL, HttpMethod.PUT, updatedStudent, StudentDto.class);
        } catch (HttpClientErrorException e) {
            expectedException = e;
        }
    }

    @Then("UserNotFoundException is thrown")
    public void userNotFoundExceptionIsThrown() {
        assertNotNull(expectedException);
        assertTrue(Objects.requireNonNull(expectedException.getMessage()).contains("User id not found: " + idOfNotExistingStudent));
    }

    private static Student createStudent() {
        Student student = new Student();
        student.setCourse(1);
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL);
        student.setPassword(PASSWORD);
        student.setStatus(Status.ACTIVE);
        student.setRole(Role.STUDENT);
        return student;
    }

    private static StudentDto createStudentDto() {
        StudentDto student = new StudentDto();
        student.setCourse(1);
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL);
        student.setPassword(PASSWORD);
        student.setStatus(Status.ACTIVE);
        student.setRole(Role.STUDENT);
        return student;
    }
}
