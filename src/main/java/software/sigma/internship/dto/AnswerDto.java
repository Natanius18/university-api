package software.sigma.internship.dto;

import lombok.Data;

@Data
public class AnswerDto {
    private Long id;
    private boolean isCorrect;
    private String option;
}
