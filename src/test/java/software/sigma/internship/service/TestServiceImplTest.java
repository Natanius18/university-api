package software.sigma.internship.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.sigma.internship.UniversityApplication;
import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.enums.CountStrategy;
import software.sigma.internship.enums.Role;
import software.sigma.internship.enums.Status;
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.service.impl.TestServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UniversityApplication.class)
class TestServiceImplTest {
    @Autowired
    private TestServiceImpl testService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ModelMapper mapper;

    private final String FIRST_NAME = "Ivan";
    private final String LAST_NAME = "Ivanov";

    @AfterEach
    public void clearDataBase(){
        teacherRepository.deleteAll();
    }

    @Test
    void save_whenSaveAndRetrieve_thenReturnDtoWithNewId(){
        Teacher teacher = createAndSaveTeacher();
        TeacherDto teacherDto = mapper.map(teacher, TeacherDto.class);

        QuestionDto question1 = getQuestionDto("Question 1");
        QuestionDto question2 = getQuestionDto("Question 2");

        TestDto testDto = new TestDto();
        testDto.setName("Test");
        testDto.setTeacher(teacherDto);
        testDto.setQuestions(List.of(question1, question2));

        TestDto savedTest = testService.save(testDto);
        assertNotNull(savedTest);
        TestDto foundTest = testService.findByIdForTeacher(savedTest.getId());
        assertEquals(savedTest, foundTest);
    }

    @Test
    void save_whenUpdateAndRetrieve_thenReturnUpdatedTestWithSameId(){
        Teacher teacher = createAndSaveTeacher();
        TeacherDto teacherDto = mapper.map(teacher, TeacherDto.class);

        QuestionDto question1 = getQuestionDto("Question 1");
        QuestionDto question2 = getQuestionDto("Question 2");

        TestDto testDto = new TestDto();
        testDto.setName("Test");
        testDto.setTeacher(teacherDto);
        testDto.setQuestions(List.of(question1, question2));

        TestDto savedTest = testService.save(testDto);
        TestDto foundTest = testService.findByIdForTeacher(savedTest.getId());

        foundTest.getQuestions().remove(0);

        TestDto updatedTest = testService.save(foundTest);
        assertEquals(foundTest, updatedTest);
        assertEquals(1, updatedTest.getQuestions().size());
        assertEquals(savedTest.getId(), updatedTest.getId());
    }

    private List<AnswerDto> getAnswerDtoList(){
        AnswerDto answer1 = getAnswerDto(true);
        AnswerDto answer2 = getAnswerDto(false);
        AnswerDto answer3 = getAnswerDto(false);
        AnswerDto answer4 = getAnswerDto(false);
        return List.of(answer1, answer2, answer3, answer4);
    }

    private QuestionDto getQuestionDto(String text) {
        QuestionDto question = new QuestionDto();
        question.setText(text);
        question.setType(CountStrategy.ONE_CORRECT_ANSWER);
        question.setAnswers(getAnswerDtoList());
        return question;
    }

    private AnswerDto getAnswerDto(boolean correct) {
        AnswerDto answer = new AnswerDto();
        answer.setOption("Option");
        answer.setCorrect(correct);
        return answer;
    }

    private Teacher createAndSaveTeacher() {
        Teacher teacher = new Teacher();
        teacher.setPosition(Teacher.Position.DOCENT);
        teacher.setFirstName(FIRST_NAME);
        teacher.setLastName(LAST_NAME);
        teacher.setEmail("teacher@gmail.com");
        teacher.setPassword("password");
        teacher.setStatus(Status.ACTIVE);
        teacher.setRole(Role.TEACHER);
        return teacherRepository.save(teacher);
    }
}
