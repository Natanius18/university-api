package software.sigma.internship.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import software.sigma.internship.validator.ValidCourse;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    @ValidCourse
    private Integer course;
}
