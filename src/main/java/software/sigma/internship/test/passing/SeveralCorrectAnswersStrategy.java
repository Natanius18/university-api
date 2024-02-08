package software.sigma.internship.test.passing;

import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.service.CounterStrategy;

import java.util.List;

public class SeveralCorrectAnswersStrategy implements CounterStrategy {

    @Override
    public float count(QuestionDto questionDto, List<AnswerDto> response) {
        var score = 0f;
        var rightAnswers = questionDto.getAnswers();
        for (AnswerDto answer : rightAnswers) {
            if (answer.getCorrect() == null) {
                throw new IllegalArgumentException("Field 'correct' cannot be null");
            }
            if (answer.getCorrect() && response.contains(answer) ||
                    !answer.getCorrect() && !response.contains(answer)) {
                score++;
            }
        }
        return score / rightAnswers.size();
    }
}
