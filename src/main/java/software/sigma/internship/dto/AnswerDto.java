package software.sigma.internship.dto;

import lombok.Data;
import software.sigma.internship.entity.Question;

@Data
public class AnswerDto {
    private Long id;
    private Question question;
    private boolean isCorrect;
    private String option;
}
