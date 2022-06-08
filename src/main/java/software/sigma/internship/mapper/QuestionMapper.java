package software.sigma.internship.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.entity.Answer;
import software.sigma.internship.entity.Question;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionMapper {
    AnswerMapper answerMapper;

    public Question toEntity(QuestionDto dto) {
        Question entity = new Question();
        entity.setId(dto.getId());
        entity.setText(dto.getText());
        entity.setType(dto.getType());
        List<Answer> answers = dto.getAnswers()
                .stream()
                .map(answerDto -> {
                    Answer answer = answerMapper.toEntity(answerDto);
                    answer.setQuestion(entity);
                    return answer;
                }).collect(Collectors.toList());
        entity.setAnswers(answers);
        return entity;
    }

    public QuestionDto toDto(Question entity) {
        QuestionDto dto = new QuestionDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setText(entity.getText());
        dto.setAnswers(entity.getAnswers()
                .stream()
                .map(answerMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }
}