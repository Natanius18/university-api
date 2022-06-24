package software.sigma.internship.service;

import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.dto.QuestionDto;

import java.util.List;

public interface CounterStrategy {
    float count(QuestionDto questionDto, List<AnswerDto> response);
}
