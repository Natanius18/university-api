package software.sigma.internship.test.passing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.dto.ResponseDto;
import software.sigma.internship.enums.CountStrategy;
import software.sigma.internship.service.CounterStrategy;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScoreCounter {

    private final Map<CountStrategy, CounterStrategy> counterStrategyMap;

    public float countResult(ResponseDto response) {
        float result = 0;
        List<QuestionDto> questions = response.getTest().getQuestions();
        for (QuestionDto question : questions) {
            result += counterStrategyMap.get(question.getType()).count(question, response.getAnswers());
        }
        return result;
    }

}
