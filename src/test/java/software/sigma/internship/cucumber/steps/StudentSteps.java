package software.sigma.internship.cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import software.sigma.internship.UniversityApplication;
import software.sigma.internship.dto.StudentDto;
import software.sigma.internship.enums.Role;
import software.sigma.internship.enums.Status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = UniversityApplication.class)
public class StudentSteps {
    @Value("${endpoint.student.basic.url}")
    private String URL;
    @Value("${endpoint.login.basic.url}")
    private String LOGIN_URL;
    private StudentDto createdStudentDto = new StudentDto();
    private final HttpHeaders reqHeaders = new HttpHeaders();
    private static final String FIRST_NAME = "First";
    private static final String LAST_NAME = "Last";
    private static final String EMAIL = "email@example.com";
    private static final String PASSWORD = "Password";
    private final RestTemplate restTemplate = new RestTemplate();
    private final List<StudentDto> existingStudents = new ArrayList<>();
    private final List<StudentDto> foundStudents = new ArrayList<>();
    private HttpClientErrorException expectedException;

    @DataTableType
    public StudentDto studentDtoEntry(Map<String, String> entry) {
        return createStudentDto(
                entry.get("firstName"),
                entry.get("lastName"),
                entry.get("email")
        );
    }

    @When("new student is added")
    public void newStudentIsRegistered() throws IOException {
        var requestJson = new String(Files.readAllBytes(Paths.get("src/test/resources/student.json")));
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var entity = new HttpEntity<>(requestJson, headers);
        createdStudentDto = restTemplate.postForObject(URL, entity, StudentDto.class);
    }

    @Then("the student is present in the database")
    public void newAccountIsCreatedAndSavedInTheDatabase() {
        authenticateUser("admin@admin.com", "admin");
        var foundStudent = restTemplate.exchange(
                URL + "/" + createdStudentDto.getId(), HttpMethod.GET, new HttpEntity<>(reqHeaders), StudentDto.class).getBody();
        assertEquals(createdStudentDto.getId(), foundStudent.getId());
        assertEquals(createdStudentDto.getFirstName(), foundStudent.getFirstName());
        assertEquals(createdStudentDto.getLastName(), foundStudent.getLastName());
        assertEquals(createdStudentDto.getEmail(), foundStudent.getEmail());
        assertEquals(createdStudentDto.getId(), foundStudent.getId());
        assertEquals(createdStudentDto.getCourse(), foundStudent.getCourse());
        restTemplate.exchange(
                URL + "/" + createdStudentDto.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(reqHeaders),
                StudentDto.class
        );
    }

    @Given("the user is authenticated with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void authenticateUser(String email, String password) {

        var requestJson = "{" +
                "\"email\": \"" + email + "\"," +
                "\"password\": \"" + password + "\"" +
                "}";
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var entity = new HttpEntity<>(requestJson, headers);

        var authRes = restTemplate.exchange(LOGIN_URL, HttpMethod.POST, entity, Object.class);

        var map = (Map<String, String>) authRes.getBody();

        reqHeaders.add("Authorization", map.get("token"));
    }

    @Given("there is a student in the database")
    public void thereIsAStudentInTheDatabase(List<StudentDto> students) {
        authenticateUser("admin@admin.com", "admin");
        students.forEach(student -> {
            var existingStudent = restTemplate.postForObject(URL, student, StudentDto.class);
            assertNotNull(restTemplate.exchange(
                    URL + "/" + existingStudent.getId(),
                    HttpMethod.GET,
                    new HttpEntity<>(reqHeaders),
                    StudentDto.class).getBody());
            existingStudents.add(existingStudent);
        });
    }

    @When("trying to retrieve an existing student")
    public void tryingToRetrieveAnExistingStudent() {
        for (StudentDto student : existingStudents) {
            var foundStudent = restTemplate.exchange(
                    URL + "/" + student.getId(), HttpMethod.GET, new HttpEntity<>(reqHeaders), StudentDto.class).getBody();
            foundStudents.add(foundStudent);
        }
    }

    @Then("getting an existing student")
    public void gettingAnExistingStudent(List<StudentDto> students) {
        authenticateUser("admin@admin.com", "admin");
        for (var i = 0; i < students.size(); i++) {
            assertEquals(students.get(i).getEmail(), foundStudents.get(i).getEmail());
            assertEquals(students.get(i).getFirstName(), foundStudents.get(i).getFirstName());
            assertEquals(students.get(i).getLastName(), foundStudents.get(i).getLastName());
            restTemplate.exchange(
                    URL + "/" + foundStudents.get(i).getId(),
                    HttpMethod.DELETE,
                    new HttpEntity<>(reqHeaders),
                    void.class);
        }

    }

    @When("existing student is updated")
    public void existingStudentIsUpdated() throws JsonProcessingException {
        var existingStudent = existingStudents.get(0);
        existingStudent.setPassword("pwd");
        existingStudent.setCourse(3);
        existingStudent.setLastName("Changed");
        var objectMapper = new ObjectMapper();
        var requestJson = objectMapper.writeValueAsString(existingStudent);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        createdStudentDto = restTemplate.postForObject(URL, entity, StudentDto.class);
    }

    @Then("updated student is saved in the database")
    public void updatedStudentIsSavedInTheDatabase() {
        authenticateUser("natan.chachko@stud.onu.edu.ua", "12345");
        var existingStudent = existingStudents.get(0);
        var updatedStudent = restTemplate.exchange(
                URL + "/" + existingStudent.getId(),
                HttpMethod.GET,
                new HttpEntity<>(reqHeaders),
                StudentDto.class
        ).getBody();
        assertEquals(existingStudent.getId(), updatedStudent.getId());
        assertEquals(existingStudent.getCourse(), updatedStudent.getCourse());
        assertEquals(existingStudent.getEmail(), updatedStudent.getEmail());
        assertEquals(existingStudent.getFirstName(), updatedStudent.getFirstName());
        assertEquals(existingStudent.getLastName(), updatedStudent.getLastName());

        restTemplate.exchange(
                URL + "/" + existingStudent.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(reqHeaders),
                void.class
        );
    }

    @Given("there is no student in the database with given id")
    public void thereIsNoStudentInTheDatabaseWithGivenId() {
        authenticateUser("admin@admin.com", "admin");
        var exception = assertThrows(HttpClientErrorException.class, () -> restTemplate.exchange(
                URL + "/9999",
                HttpMethod.GET,
                new HttpEntity<>(reqHeaders),
                StudentDto.class)
        );

        var expectedMessage = "User id not found: 9999";
        var actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));


        assertThrows(HttpClientErrorException.class, () -> restTemplate.exchange(
                URL + "/9999",
                HttpMethod.GET,
                new HttpEntity<>(reqHeaders),
                StudentDto.class));
    }

    @When("not existing student is updated")
    public void notExistingStudentIsUpdated() {
        var studentDto = createStudentDto(FIRST_NAME, LAST_NAME, EMAIL);
        studentDto.setId(9999L);

        var updatedStudent = new HttpEntity<>(studentDto);

        try {
            restTemplate.exchange(URL, HttpMethod.PUT, updatedStudent, StudentDto.class);
        } catch (HttpClientErrorException e) {
            expectedException = e;
        }
    }

    @Then("UserNotFoundException is thrown")
    public void userNotFoundExceptionIsThrown() {
        assertNotNull(expectedException);
        assertTrue(Objects.requireNonNull(expectedException.getMessage()).contains("User id not found: 9999"));
    }

    private static StudentDto createStudentDto(String firstName, String lastName, String email) {
        var student = new StudentDto();
        student.setCourse(1);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPassword(PASSWORD);
        student.setStatus(Status.ACTIVE);
        student.setRole(Role.STUDENT);
        return student;
    }
}
