package software.sigma.internship.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {
    private Long id;
    private StudentDto student;
    private int numberOfTry;
    private TestDto test;
    private List<AnswerDto> answers;
    private float result;
}
