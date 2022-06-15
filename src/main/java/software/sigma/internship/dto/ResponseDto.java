package software.sigma.internship.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {
    private Long id;
    private Long studentId;
    private int numberOfTry;
    private Long testId;
    private List<AnswerDto> answers;
}
