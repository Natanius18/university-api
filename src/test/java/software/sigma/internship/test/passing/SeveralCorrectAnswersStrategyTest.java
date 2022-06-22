package software.sigma.internship.test.passing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.dto.QuestionDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SeveralCorrectAnswersStrategyTest {

    private final QuestionDto questionDto = new QuestionDto();
    private List<AnswerDto> response;
    private final AnswerDto answer1 = new AnswerDto();
    private final AnswerDto answer2 = new AnswerDto();
    private final AnswerDto answer3 = new AnswerDto();
    private final AnswerDto answer4 = new AnswerDto();
    private final AnswerDto answer5 = new AnswerDto();

    @InjectMocks
    private SeveralCorrectAnswersStrategy strategy;

    @Before
    public void setup(){

        answer1.setId(1L);
        answer1.setCorrect(true);

        answer2.setId(2L);
        answer2.setCorrect(true);

        answer3.setId(3L);
        answer3.setCorrect(false);

        answer4.setId(4L);
        answer4.setCorrect(false);

        answer5.setId(5L);
        answer5.setCorrect(false);

        List<AnswerDto> rightAnswers = List.of(answer1, answer2, answer3, answer4);
        questionDto.setAnswers(rightAnswers);
    }

    @Test
    public void countShouldReturn1(){
        response = List.of(answer1, answer2, answer5);
        assertThat(strategy.count(questionDto, response)).isEqualTo(1);
    }

    @Test
    public void countShouldReturn075(){
        response = List.of(answer1);
        assertThat(strategy.count(questionDto, response)).isEqualTo(0.75f);
    }

    @Test
    public void countShouldReturn05(){
        response = List.of(answer1, answer3);
        assertThat(strategy.count(questionDto, response)).isEqualTo(0.5f);
    }

    @Test
    public void countShouldReturn025(){
        response = List.of(answer3);
        assertThat(strategy.count(questionDto, response)).isEqualTo(0.25f);
    }

    @Test
    public void countShouldReturnZero(){
        response = List.of(answer3, answer4);
        assertThat(strategy.count(questionDto, response)).isZero();
    }

}