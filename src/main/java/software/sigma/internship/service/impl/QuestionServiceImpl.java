package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.entity.Question;
import software.sigma.internship.repo.QuestionRepository;
import software.sigma.internship.service.QuestionService;
import software.sigma.internship.validator.exception.QuestionNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final ModelMapper questionMapper;

    @Override
    public List<QuestionDto> findAll() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(question -> {
                    QuestionDto questionDto = questionMapper.map(question, QuestionDto.class);
                    questionDto.setAnswers(null);
                    return questionDto;
                }).collect(Collectors.toList());
    }

    @Override
    public QuestionDto findById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        return questionMapper.map(question, QuestionDto.class);
    }

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
