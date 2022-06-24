package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.entity.Question;
import software.sigma.internship.repo.QuestionRepository;
import software.sigma.internship.service.QuestionService;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final ModelMapper questionMapper;

    @Override
    public QuestionDto save(QuestionDto questionDto) {
        Question newQuestion = questionRepository.save(questionMapper.map(questionDto, Question.class));
        return questionMapper.map(newQuestion, QuestionDto.class);
    }

    @Override
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public void deleteQuestionByIdIn(List<Long> idList) {
        questionRepository.deleteQuestionByIdIn(idList);
    }
}
