package software.sigma.internship.dto;

import lombok.Data;
import software.sigma.internship.entity.Teacher;


@Data
public class TeacherDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Teacher.Position position;
}
