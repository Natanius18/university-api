package software.sigma.internship.service;

import software.sigma.internship.dto.AnswerDto;

import java.util.List;

public interface AnswerService {
    List<AnswerDto> findAll();

    AnswerDto findById(Long id);

    AnswerDto save(AnswerDto answerDto);

    void deleteById(Long id);
}
