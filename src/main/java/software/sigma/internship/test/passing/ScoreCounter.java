package software.sigma.internship.test.passing;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.dto.ResponseDto;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ScoreCounter {
    private Map<Integer, CounterStrategy> counterStrategyMap;

    public float countResult(ResponseDto response) {
        float result = 0;
        List<QuestionDto> questions = response.getTest().getQuestions();
        for (QuestionDto question : questions) {
            result += counterStrategyMap.get(question.getType()).count(question, response.getAnswers());
        }
        return result;
    }

}
