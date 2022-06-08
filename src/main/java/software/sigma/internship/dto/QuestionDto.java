package software.sigma.internship.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    private Long id;
    private int type;
    private String text;
    private List<AnswerDto> answers;
}
