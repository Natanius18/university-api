package software.sigma.internship.dto;

import lombok.Data;
import software.sigma.internship.entity.Answer;
import software.sigma.internship.entity.Test;

import java.util.List;

@Data
public class QuestionDto {
    private Long id;
    private Test test;
    private int type;
    private String text;
    private List<Answer> answers;
}
