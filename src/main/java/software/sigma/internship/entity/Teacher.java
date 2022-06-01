package software.sigma.internship.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorValue("Teacher")
public class Teacher extends Person {
    private Position position;

    public enum Position {
        HEAD_TEACHER,
        DOCENT,
        PROFESSOR
    }
}