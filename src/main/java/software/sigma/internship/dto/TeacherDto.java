package software.sigma.internship.dto;

import lombok.Data;
import software.sigma.internship.entity.Teacher;


@Data
public class TeacherDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer course;
    private Teacher.Position position;

    public Teacher toEntity() {
        Teacher entity = new Teacher();

        entity.setId(this.id);
        entity.setFirstName(this.firstName);
        entity.setLastName(this.lastName);
        entity.setPosition(this.position);
        return entity;
    }
}
