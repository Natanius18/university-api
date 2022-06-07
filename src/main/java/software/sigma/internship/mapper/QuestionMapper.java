package software.sigma.internship.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.entity.Question;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionMapper {
    AnswerMapper answerMapper;

    public Question toEntity(QuestionDto dto) {
        Question entity = new Question();
        entity.setId(dto.getId());
        entity.setTest(dto.getTest());
        entity.setText(dto.getText());
        entity.setType(dto.getType());
        entity.setAnswers(null);
        return entity;
    }

    public QuestionDto toDto(Question entity) {
        QuestionDto dto = new QuestionDto();
        dto.setId(entity.getId());
        dto.setTest(null);
        dto.setType(entity.getType());
        dto.setText(entity.getText());
        dto.setAnswers(entity.getAnswers().stream()
                .map(answer -> {
                    answer.setQuestion(null);
                    return answerMapper.toDto(answer);
                })
                .collect(Collectors.toList()));
        return dto;
    }
}