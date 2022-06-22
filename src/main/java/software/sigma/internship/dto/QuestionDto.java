package software.sigma.internship.dto;

import lombok.Data;
import software.sigma.internship.enums.CountStrategy;

import java.util.List;

@Data
public class QuestionDto {
    private Long id;
    private CountStrategy type;
    private String text;
    private List<AnswerDto> answers;
}
