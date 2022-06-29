package software.sigma.internship.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private Long id;
    private StudentDto student;
    private int numberOfTry;
    private TestDto test;
    private List<AnswerDto> answers;
    private float result;
}
