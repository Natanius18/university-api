package software.sigma.internship.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.sigma.internship.validator.ValidCourse;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto extends PersonDto {
    @ValidCourse
    private Integer course;

    @Override
    public String toString() {
        return "StudentDto{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role=" + getRole() +
                ", status=" + getStatus() +
                ", course=" + course + '\'' +
                '}';
    }
}
