package software.sigma.internship.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestDto {
    private Long id;
    private String name;
    private TeacherDto teacher;
    private List<QuestionDto> questions;
}
