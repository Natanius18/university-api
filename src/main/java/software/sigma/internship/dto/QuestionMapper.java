package software.sigma.internship.dto;

import org.springframework.stereotype.Service;
import software.sigma.internship.entity.Question;

import java.util.stream.Collectors;

@Service
public class QuestionMapper {

    public Question toEntity(QuestionDto dto) {
        Question entity = new Question();
        entity.setId(dto.getId());
        entity.setTest(null);
        entity.setType(dto.getType());
        entity.setAnswers(dto.getAnswers());              //TODO can be a problem here
        return entity;
    }

    public QuestionDto toDto(Question entity) {
        QuestionDto dto = new QuestionDto();
        dto.setId(entity.getId());
        dto.setTest(null);
        dto.setAnswers(entity.getAnswers().stream()
                .peek(answer -> answer.setQuestion(null))
                .collect(Collectors.toList()));
        dto.setText(entity.getText());
        dto.setType(entity.getType());
        return dto;
    }
}