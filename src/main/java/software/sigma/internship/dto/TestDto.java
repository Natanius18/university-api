package software.sigma.internship.dto;

import lombok.Data;
import software.sigma.internship.entity.Teacher;

import java.util.List;

@Data
public class TestDto {
    private Long id;
    private String name;
    private Teacher teacher;
    private List<QuestionDto> questions;
}
