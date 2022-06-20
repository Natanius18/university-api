package software.sigma.internship.service;

import software.sigma.internship.dto.QuestionDto;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> findAll();

    QuestionDto findById(Long id);

    QuestionDto save(QuestionDto questionDto);

    void deleteById(Long id);

    void deleteQuestionByIdIn(List<Long> idList);
}
