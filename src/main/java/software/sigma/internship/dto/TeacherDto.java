package software.sigma.internship.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.sigma.internship.entity.Teacher;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherDto extends PersonDto {
    private Teacher.Position position;

    @Override
    public String toString() {
        return "TeacherDto{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role=" + getRole() +
                ", status=" + getStatus() +
                ", position=" + position + '\'' +
                '}';
    }
}