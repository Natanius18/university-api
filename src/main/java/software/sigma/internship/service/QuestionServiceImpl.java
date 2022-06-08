package software.sigma.internship.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.entity.Question;
import software.sigma.internship.mapper.QuestionMapper;
import software.sigma.internship.repo.QuestionRepository;
import software.sigma.internship.validator.exception.QuestionNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private  final QuestionRepository questionRepository;
    private  final QuestionMapper questionMapper;

    @Override
    public List<QuestionDto> findAll() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(question -> {
                    QuestionDto questionDto = questionMapper.toDto(question);
                    questionDto.setAnswers(null);
                    return questionDto;
                }).collect(Collectors.toList());
    }

    @Override
    public QuestionDto findById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        return questionMapper.toDto(question);
    }

    @Override
    public QuestionDto save(QuestionDto questionDto) {
        Question newQuestion = questionRepository.save(questionMapper.toEntity(questionDto));
        return questionMapper.toDto(newQuestion);
    }

    @Override
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }
}
