package software.sigma.internship.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.entity.Answer;
import software.sigma.internship.entity.Question;
import software.sigma.internship.mapper.AnswerMapper;
import software.sigma.internship.mapper.QuestionMapper;
import software.sigma.internship.repo.QuestionRepository;
import software.sigma.internship.validator.exception.QuestionNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    QuestionRepository questionRepository;
    QuestionMapper questionMapper;
    AnswerService answerService;
    AnswerMapper answerMapper;

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
        List<Answer> answers = questionDto.getAnswers()
                .stream()
                .map(answerDto -> {
                    answerDto.setQuestion(newQuestion);
                    return answerMapper.toEntity(answerService.save(answerDto));
                }).collect(Collectors.toList());
        newQuestion.setAnswers(answers);
        return questionMapper.toDto(newQuestion);
    }

    @Override
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }
}
