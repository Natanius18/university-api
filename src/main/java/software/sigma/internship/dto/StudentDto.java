package software.sigma.internship.dto;

import lombok.Data;
import software.sigma.internship.entity.Student;

@Data
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer course;

    public Student toEntity() {
        Student entity = new Student();

        entity.setId(this.id);
        entity.setFirstName(this.firstName);
        entity.setLastName(this.lastName);
        entity.setCourse(this.course);
        return entity;
    }

}
