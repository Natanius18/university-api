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
    private final AnswerDto a1 = new AnswerDto();
    private final AnswerDto a2 = new AnswerDto();
    private final AnswerDto a3 = new AnswerDto();
    private final AnswerDto a4 = new AnswerDto();
    private final AnswerDto a5 = new AnswerDto();

    @InjectMocks
    private SeveralCorrectAnswersStrategy strategy;

    @Before
    public void setup(){

        a1.setId(1L);
        a1.setCorrect(true);

        a2.setId(2L);
        a2.setCorrect(true);

        a3.setId(3L);
        a3.setCorrect(false);

        a4.setId(4L);
        a4.setCorrect(false);

        a5.setId(5L);
        a5.setCorrect(false);

        List<AnswerDto> rightAnswers = List.of(a1, a2, a3, a4);
        questionDto.setAnswers(rightAnswers);
    }

    @Test
    public void countShouldReturn1(){
        response = List.of(a1, a2, a5);
        assertThat(strategy.count(questionDto, response)).isEqualTo(1);
    }

    @Test
    public void countShouldReturn075(){
        response = List.of(a1);
        assertThat(strategy.count(questionDto, response)).isEqualTo(0.75f);
    }

    @Test
    public void countShouldReturn05(){
        response = List.of(a1, a3);
        assertThat(strategy.count(questionDto, response)).isEqualTo(0.5f);
    }

    @Test
    public void countShouldReturn025(){
        response = List.of(a3);
        assertThat(strategy.count(questionDto, response)).isEqualTo(0.25f);
    }

    @Test
    public void countShouldReturnZero(){
        response = List.of(a3, a4);
        assertThat(strategy.count(questionDto, response)).isZero();
    }

}