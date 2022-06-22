package software.sigma.internship.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestDto {
    private Long id;
    private String name;
    private TeacherDto teacher;
    private List<QuestionDto> questions;
}
