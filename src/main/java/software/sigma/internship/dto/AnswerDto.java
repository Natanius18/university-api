package software.sigma.internship.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDto {
    private Long id;
    private Boolean isCorrect;
    private String option;
}
