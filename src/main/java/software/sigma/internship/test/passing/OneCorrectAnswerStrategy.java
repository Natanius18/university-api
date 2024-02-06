package software.sigma.internship.test.passing;

import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.service.CounterStrategy;

import java.util.List;

public class OneCorrectAnswerStrategy implements CounterStrategy {

    @Override
    public float count(QuestionDto questionDto, List<AnswerDto> answersInResponse) {
        AnswerDto rightAnswer = questionDto.getAnswers()
                .stream()
                .filter(AnswerDto::getCorrect)
                .findFirst()
                .orElseThrow();
        return (answersInResponse.contains(rightAnswer) ? 1 : 0);
    }
}
